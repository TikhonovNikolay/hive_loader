package com.atconsulting.ignitehiveloader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteInClosure;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ORC loader mapper.
 */
public class OrcLoaderMapper extends Mapper<NullWritable, OrcStruct,  NullWritable, NullWritable> {
    /** Counter group. */
    private static final String CTR_GRP = "ignite";

    /** Counter: total duration. */
    private static final String CTR_TOTAL_DUR = "dur.total";

    /** Counter: setup duration. */
    private static final String CTR_SETUP_DUR = "dur.setup";

    /** Counter: cleanup duration. */
    private static final String CTR_CLEANUP_DUR = "dur.cleanup";

    /** Counter: map count. */
    private static final String CTR_MAP_CNT = "cnt.map";

    /** Counter: map duration in milliseconds. */
    private static final String CTR_MAP_DUR = "dur.map";

    /** Counter: streamer add duration.. */
    private static final String CTR_ADD_DUR = "dur.add";

    /** Mutex for synchronization. */
    private final Object mux = new Object();

    /** Per-node buffers. */
    private final Map<UUID, Map<CHA.Key, CHA>> bufPerNodeId = new HashMap<>();

    /** Current date. */
    private final Date curDate = new Date();

    /** Current year. */
    private final int curYear = curDate.getYear();

    /** Current month. */
    private final int curMonth = curDate.getMonth();

    /** Current day. */
    private final int curDay = curDate.getDay();

    /** Ignite instance. */
    private Ignite ignite;

    /** Data streamer. */
    private IgniteDataStreamer<CHA.Key, CHA> streamer;

    /** Ignite cache. */
    private IgniteCache<CHA.Key, CHA> cache;

    /** Affinity. */
    private Affinity<Long> aff;

    /** Buffer size. */
    private int bufSize;

    /** Total parallel ops (parallelOps * node count) */
    private int totalParallelOps;

    /** Whether put should be used instead of streamer. */
    private boolean usePut;

    /** Whether data should not be put to cache. */
    private boolean skipCache;

    /** Whether only current day should be loaded. */
    private boolean filterCurDay;

    /** Unique counter ID. */
    private String ctrId;

    /** Counter: start time. */
    private long startTime;

    /** Setup duration. */
    private long ctrSetupDur;

    /** Counter: map duration. */
    private long ctrMapDur;

    /** Counter: map count. */
    private long ctrMapCnt;

    /** Counter: duration of streamer add operations. */
    private long ctrAddDur;

    /** Amount of active operations. */
    private int curParallelOps;

    /** Error ocurred during put. */
    private Exception putErr;

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override protected void setup(Context context) throws IOException, InterruptedException {
        ctrId = Integer.toString(context.getTaskAttemptID().getTaskID().getId());

        startTime = System.nanoTime();

        // Check config parameters.
        Configuration conf = context.getConfiguration();

        String cfgPath = conf.get(OrcLoaderProperties.PROP_CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalArgumentException("Path to Ignite XML configuration is not specified.");

        String cacheName = conf.get(OrcLoaderProperties.PROP_CACHE_NAME);

        bufSize = conf.getInt(OrcLoaderProperties.PROP_BUFFER_SIZE, IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE);

        if (bufSize <= 0)
            throw new IllegalArgumentException("Buffer size cannot be negative: " + bufSize);

        int parallelOps = conf.getInt(OrcLoaderProperties.PROP_PARALLEL_OPS, IgniteDataStreamer.DFLT_MAX_PARALLEL_OPS);

        if (parallelOps <= 0)
            throw new IllegalArgumentException("Parallel ops must be positive: " + parallelOps);

        usePut = conf.getBoolean(OrcLoaderProperties.PROP_USE_PUT, false);
        skipCache = conf.getBoolean(OrcLoaderProperties.PROP_SKIP_CACHE, false);
        filterCurDay = conf.getBoolean(OrcLoaderProperties.PROP_FILTER_CURRENT_DAY, false);

        // Start Ignite client.
        Ignition.setClientMode(true);

        ignite = Ignition.start(cfgPath);

        // Get and configure streamer.
        streamer = ignite.dataStreamer(cacheName);

        streamer.perNodeBufferSize(bufSize);
        streamer.perNodeParallelOperations(parallelOps);

        // Get cache and affinity for "put" mode.
        IgniteCache<CHA.Key, CHA> syncCache = ignite.cache(cacheName);

        cache = syncCache.withAsync();

        aff = ignite.affinity(cacheName);

        totalParallelOps = ignite.cluster().forDataNodes(cacheName).nodes().size();

        // Log setup duration.
        ctrSetupDur = System.nanoTime() - startTime;
    }

    /** {@inheritDoc} */
    @Override public void map(NullWritable key, OrcStruct struct, Context context) throws IOException,
        InterruptedException {
        long mapStartTime = System.nanoTime();

        CHA.Key chaKey = OrcLoaderUtils.structToKey(struct, OrcLoaderUtils.inspector());
        CHA chaVal = OrcLoaderUtils.structToValue(struct, OrcLoaderUtils.inspector());

        long addStartTime = System.nanoTime();

        boolean added = addData(chaKey, chaVal);

        long mapStopTime = System.nanoTime();

        // Update counters.
        ctrAddDur += mapStopTime - addStartTime;
        ctrMapDur += mapStopTime - mapStartTime;

        if (added)
            ctrMapCnt++;
    }

    /** {@inheritDoc} */
    @Override protected void cleanup(Context ctx) throws IOException, InterruptedException {
        long cleanupStartTime = System.nanoTime();

        if (usePut) {
            // Send remaining buffers.
            for (Map<CHA.Key, CHA> buf : bufPerNodeId.values())
                sendBuffer(buf);

            // Await all cache ops to complete.
            synchronized (mux) {
                while (curParallelOps > 0)
                    mux.wait();

                if (putErr != null)
                    throw new IgniteException("An error ocurred during put operation.", putErr);
            }
        }
        else {
            streamer.flush();
            streamer.close();
        }

        ctrAddDur += System.nanoTime() - cleanupStartTime;

        // Close the client node.
        if (ignite != null)
            ignite.close();

        long stopTime = System.nanoTime();

        counter(ctx, CTR_SETUP_DUR).setValue(ctrSetupDur / 1_000_000);
        counter(ctx, CTR_CLEANUP_DUR).setValue((stopTime - cleanupStartTime) / 1_000_000);
        counter(ctx, CTR_MAP_DUR).setValue(ctrMapDur / 1_000_000);
        counter(ctx, CTR_TOTAL_DUR).setValue((stopTime - startTime) / 1_000_000);
        counter(ctx, CTR_ADD_DUR).setValue(ctrAddDur / 1_000_000);
        counter(ctx, CTR_MAP_CNT).setValue(ctrMapCnt);
    }

    /**
     * Add data to cache.
     *
     * @param key Key.
     * @param val Value.
     * @return {@code True} if data was put to cache, {@code false} if it was filtere.
     */
    private boolean addData(CHA.Key key, CHA val) {
        if (filterCurDay && !currentDay(val.getPutTimestamp()))
            return false;

        if (skipCache)
            return true;

        if (usePut) {
            // Get affinity node.
            ClusterNode node = aff.mapKeyToNode(key.getSubscriberId());

            if (node == null)
                throw new IllegalStateException("Cannot get primary node for key (all data nodes left the cluster).");

            UUID nodeId = node.id();

            // Put data to buffer.
            Map<CHA.Key, CHA> buf = bufPerNodeId.get(nodeId);

            if (buf == null) {
                buf = new HashMap<>(bufSize, 1.0f);

                bufPerNodeId.put(nodeId, buf);
            }

            buf.put(key, val);

            // Schedule send if buffer is full.
            if (buf.size() == bufSize) {
                sendBuffer(buf);

                bufPerNodeId.remove(nodeId);
            }
        }
        else
            streamer.addData(key, val);

        return true;
    }

    /**
     * Send buffer to servers.
     *
     * @param buf Buffer.
     */
    private void sendBuffer(Map<CHA.Key, CHA> buf) {
        // Obtain permit.
        try {
            synchronized (mux) {
                while (curParallelOps >= totalParallelOps)
                    mux.wait();

                curParallelOps++;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new IgniteException("Failed to wait for asynchronous operation permit.");
        }

        // Perform put.
        cache.putAll(buf);

        IgniteFuture<Void> fut = cache.future();

        // Decrease parallel ops count when put is completed.
        fut.listen(new IgniteInClosure<IgniteFuture>() {
            @Override public void apply(IgniteFuture fut0) {
                Exception err = null;

                try {
                    fut0.get();
                }
                catch (Exception e) {
                    err = e;
                }

                synchronized (mux) {
                    if (err != null && putErr == null)
                        putErr = err;

                    curParallelOps--;

                    mux.notifyAll();
                }
            }
        });
    }

    /**
     * Check if passed date represents current day.
     *
     * @param date Date.
     * @return {@code True} if this is current date.
     */
    private boolean currentDay(Date date) {
        return date.getDay() == curDay && date.getMonth() == curMonth && date.getYear() == curYear;
    }

    /**
     * Create counter.
     *
     * @param name Counter name.
     * @return Counter.
     */
    private Counter counter(Context ctx, String name) {
        String name0 = name + "." + ctrId;

        return ctx.getCounter(CTR_GRP, name0);
    }
}
