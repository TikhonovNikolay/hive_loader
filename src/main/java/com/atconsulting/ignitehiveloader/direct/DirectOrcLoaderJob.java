package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.CHA;
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.ql.io.orc.Reader;
import org.apache.hadoop.hive.ql.io.orc.RecordReader;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.internal.util.tostring.GridToStringExclude;
import org.apache.ignite.internal.util.typedef.internal.S;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteInClosure;
import org.apache.ignite.resources.IgniteInstanceResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Ignite job to load a file into Ignite.
 */
public class DirectOrcLoaderJob implements ComputeJob {
    /** Injected Ignite instance. */
    @IgniteInstanceResource
    @GridToStringExclude
    private Ignite ignite;

    /** Path to file. */
    private String path;

    /** Cache name. */
    private String cacheName;

    /** Buffer size. */
    private int bufSize;

    /** Affinity mode flag. */
    private boolean affMode;

    /** Concurrency. */
    private int concurrency;

    /** Skip cache flag. */
    private boolean skipCache;

    /**
     * Constructor.
     */
    public DirectOrcLoaderJob() {
        // No-op.
    }

    /**
     * Constructor.
     *
     * @param path Path.
     * @param cacheName Cache name.
     * @param bufSize Buffer size.
     * @param affMode Affinity mode.
     * @param concurrency Concurrency level.
     * @param skipCache Skip cache flag.
     */
    public DirectOrcLoaderJob(String path, String cacheName, int bufSize, boolean affMode, int concurrency,
        boolean skipCache) {
        this.path = path;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.affMode = affMode;
        this.concurrency = concurrency;
        this.skipCache = skipCache;
    }

    /** {@inheritDoc} */
    @Override public Object execute() throws IgniteException {
        System.out.println(">>> Starting ORC job: " + this);

        int rowCnt = 0;

        Reader reader = DirectOrcLoaderUtils.readerForPath(path);

        StructObjectInspector inspector = (StructObjectInspector)reader.getObjectInspector();

        if (affMode)
            return loadWithAffinity(reader, inspector);

        try (IgniteDataStreamer<CHA.Key, CHA> streamer = ignite.dataStreamer(cacheName)) {
            streamer.perNodeBufferSize(bufSize);

            try {
                RecordReader rows = reader.rows();

                OrcStruct row = null;

                while (rows.hasNext()) {
                    row = (OrcStruct)rows.next(row);

                    CHA.Key key = DirectOrcLoaderUtils.structToKey(row, inspector);
                    CHA val = DirectOrcLoaderUtils.structToValue(row, inspector);

                    if (!skipCache)
                        streamer.addData(key, val);

                    rowCnt++;
                }
            }
            catch (Exception e) {
                throw new IgniteException("Failed to load ORC data to cache.", e);
            }
        }

        return rowCnt;
    }

    /**
     * Perform load with affinity mode.
     *
     * @param reader Reader.
     * @param inspector Inspector.
     * @return Amount of loaded key-value pairs.
     */
    private int loadWithAffinity(Reader reader, StructObjectInspector inspector) {
        IgniteCache<CHA.Key, CHA> cache = ignite.cache(cacheName);

        int rowCnt = 0;

        Map<CHA.Key, CHA> buf = new HashMap<>(bufSize, 1.0f);

        ClusterNode locNode = ignite.cluster().localNode();

        Affinity<CHA.Key> aff = ignite.affinity(cacheName);

        try {
            RecordReader rows = reader.rows();

            OrcStruct row = null;

            while (rows.hasNext()) {
                row = (OrcStruct)rows.next(row);

                CHA.Key key = DirectOrcLoaderUtils.structToKey(row, inspector);

                if (affMode && !aff.isPrimary(locNode, key))
                    continue;

                CHA val = DirectOrcLoaderUtils.structToValue(row, inspector);

                buf.put(key, val);

                if (buf.size() == bufSize) {
                    loadAffinityBatch(cache, buf);

                    buf = new HashMap<>(bufSize, 1.0f);
                }

                rowCnt++;
            }

            if (buf.size() > 0)
                loadAffinityBatch(cache, buf);

            // Await for async ops completion.
            try {
                synchronized (this) {
                    while (asyncOps > 0)
                        wait();
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                throw new IgniteException("Interrupted while waiting for async ops completion!", e);
            }
        }
        catch (Exception e) {
            throw new IgniteException("Failed to load ORC data to cache.", e);
        }

        return rowCnt;
    }

    /** Current async operations. */
    private int asyncOps;

    /** Error ocurred during async execution. */
    private Exception asyncErr;

    /**
     * Load batch in affinity mode.
     *
     * @param buf Buffer.
     */
    private void loadAffinityBatch(IgniteCache<CHA.Key, CHA> cache, Map<CHA.Key, CHA> buf) {
        if (skipCache)
            return;

        if (concurrency <= 0)
            cache.putAll(buf);
        else {
            // Increment async ops.
            try {
                synchronized (this) {
                    while (asyncOps >= concurrency)
                        wait();

                    asyncOps++;
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                throw new IgniteException("Interrupted while waiting for execution permit!", e);
            }

            // Perform asynch put.
            IgniteCache<CHA.Key, CHA> cacheAsync = cache.withAsync();

            cacheAsync.putAll(buf);

            cacheAsync.future().listen(new IgniteInClosure<IgniteFuture<Object>>() {
                @Override public void apply(IgniteFuture<Object> fut) {
                    IgniteException err = null;

                    try {
                        fut.get();
                    }
                    catch (IgniteException e) {
                        err = e;
                    }

                    if (err != null)
                        System.out.println(">>> Error occurred: " + err);

                    // Decrement async ops.
                    synchronized (this) {
                        if (asyncErr == null && err != null)
                            asyncErr = err;

                        asyncOps--;

                        notifyAll();
                    }
                }
            });
        }
    }

    /** {@inheritDoc} */
    @Override public void cancel() {
        // No-op.
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DirectOrcLoaderJob.class, this);
    }
}
