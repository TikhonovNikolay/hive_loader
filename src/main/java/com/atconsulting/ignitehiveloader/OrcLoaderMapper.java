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

    /** Counter: start duration. */
    private static final String CTR_START_DUR = "start.dur";

    /** Counter: load count. */
    private static final String CTR_LOAD_CNT = "load.cnt";

    /** Counter: load duration. */
    private static final String CTR_LOAD_DUR = "load.dur";

    /** Counter: stop duration. */
    private static final String CTR_STOP_DUR = "stop.dur";

    /** Counter: total duration. */
    private static final String CTR_TOTAL_DUR = "total.dur";

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

    /** Loader mode. */
    private OrcLoaderMode mode;

    /** Whether only current day should be loaded. */
    private boolean filterCurDay;

    /** Unique counter ID. */
    private String ctrId;

    /** Counter: start time. */
    private long startTime;

    /** Setup duration. */
    private long ctrStartDur;

    /** Counter: load count. */
    private long ctrLoadCnt;

    /** Counter: load duration. */
    private long ctrLoadDur;

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

        mode = conf.getEnum(OrcLoaderProperties.PROP_MODE, OrcLoaderMode.STREAMER);

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
        ctrStartDur = System.nanoTime() - startTime;
    }

    /** {@inheritDoc} */
    @Override public void map(NullWritable key, OrcStruct struct, Context context) throws IOException,
        InterruptedException {
        long mapStartTime = System.nanoTime();

        CHA.Key chaKey = OrcLoaderUtils.structToKey(struct, OrcLoaderUtils.inspector());
        CHA chaVal = OrcLoaderUtils.structToValue(struct, OrcLoaderUtils.inspector());

        boolean added = addData(chaKey, chaVal);

        // Update counters.
        ctrLoadDur += System.nanoTime() - mapStartTime;

        if (added)
            ctrLoadCnt++;
    }

    /** {@inheritDoc} */
    @Override protected void cleanup(Context ctx) throws IOException, InterruptedException {
        long flushStartTime = System.nanoTime();

        if (mode == OrcLoaderMode.STREAMER || mode == OrcLoaderMode.STREAMER_BATCHED) {
            streamer.flush();
            streamer.close();
        }
        else if (mode == OrcLoaderMode.PUT) {
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

        long flushStopTime = System.nanoTime();

        // Close the client node.
        if (ignite != null)
            ignite.close();

        long stopTime = System.nanoTime();

        ctrLoadDur += flushStopTime - flushStartTime;

        counter(ctx, CTR_START_DUR).setValue(ctrStartDur / 1_000_000);
        counter(ctx, CTR_STOP_DUR).setValue((stopTime - flushStopTime) / 1_000_000);
        counter(ctx, CTR_TOTAL_DUR).setValue((stopTime - startTime) / 1_000_000);
        counter(ctx, CTR_LOAD_DUR).setValue(ctrLoadDur / 1_000_000);
        counter(ctx, CTR_LOAD_CNT).setValue(ctrLoadCnt);
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

        if (mode == OrcLoaderMode.SKIP)
            return true;
        else if (mode == OrcLoaderMode.STREAMER)
            streamer.addData(key, val);
        else {
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

        return true;
    }

    /**
     * Send buffer to servers.
     *
     * @param buf Buffer.
     */
    private void sendBuffer(Map<CHA.Key, CHA> buf) {
        if (mode == OrcLoaderMode.STREAMER_BATCHED)
            streamer.addData(buf);
        else {
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
