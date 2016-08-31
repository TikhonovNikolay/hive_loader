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
import org.apache.ignite.resources.IgniteInstanceResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Ignite job to load a file into Ignite.
 */
public class DirectOrcLoaderJob implements ComputeJob {
    /** Path to file. */
    private String path;

    /** Cache name. */
    private String cacheName;

    /** Buffer size. */
    private int bufSize;

    /** Affinity mode flag. */
    private boolean affMode;

    /** Skip cache flag. */
    private boolean skipCache;

    /** Injected Ignite instance. */
    @IgniteInstanceResource
    @GridToStringExclude
    private Ignite ignite;

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
     * @param skipCache Skip cache flag.
     */
    public DirectOrcLoaderJob(String path, String cacheName, int bufSize, boolean affMode, boolean skipCache) {
        this.path = path;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.affMode = affMode;
        this.skipCache = skipCache;
    }

    /** {@inheritDoc} */
    @Override public Object execute() throws IgniteException {
        System.out.println(">>> Starting ORC job: " + this);

        long cnt;

        long startTime = System.currentTimeMillis();

        Reader reader = DirectOrcLoaderUtils.readerForPath(path);

        StructObjectInspector inspector = (StructObjectInspector)reader.getObjectInspector();

        if (affMode)
            cnt = loadWithAffinity(reader, inspector);
        else
            cnt = loadWithoutAffinity(reader, inspector);

        long dur = (System.currentTimeMillis() - startTime) / 1_000_000;

        return new DirectOrcLoaderJobResult(this.toString(), ignite.cluster().localNode().id(), cnt, dur);
    }

    /**
     * Perform load without affinity mode.
     *
     * @param reader Reader.
     * @param inspector Inspector.
     * @return Amount of loaded key-value pairs.
     */
    private long loadWithoutAffinity(Reader reader, StructObjectInspector inspector) {
        long cnt = 0;

        try (IgniteDataStreamer<CHA.Key, CHA> streamer = ignite.dataStreamer(cacheName)) {
            streamer.perNodeBufferSize(bufSize);

            try {
                RecordReader rows = reader.rows();

                OrcStruct row = null;

                while (rows.hasNext()) {
                    row = (OrcStruct) rows.next(row);

                    CHA.Key key = DirectOrcLoaderUtils.structToKey(row, inspector);
                    CHA val = DirectOrcLoaderUtils.structToValue(row, inspector);

                    if (!skipCache)
                        streamer.addData(key, val);

                    cnt++;
                }
            }
            catch (Exception e) {
                throw new IgniteException("Failed to load ORC data to cache.", e);
            }
        }

        return cnt;
    }

    /**
     * Perform load with affinity mode.
     *
     * @param reader Reader.
     * @param inspector Inspector.
     * @return Amount of loaded key-value pairs.
     */
    private long loadWithAffinity(Reader reader, StructObjectInspector inspector) {
        IgniteCache<CHA.Key, CHA> cache = ignite.cache(cacheName);

        long cnt = 0;

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
                    if (!skipCache)
                        cache.putAll(buf);

                    buf = new HashMap<>(bufSize, 1.0f);
                }

                cnt++;
            }

            if (buf.size() > 0) {
                if (!skipCache)
                    cache.putAll(buf);
            }
        }
        catch (Exception e) {
            throw new IgniteException("Failed to load ORC data to cache.", e);
        }

        return cnt;
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
