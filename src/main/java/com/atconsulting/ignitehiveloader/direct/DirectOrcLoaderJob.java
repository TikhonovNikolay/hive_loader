package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.CHA;
import com.atconsulting.ignitehiveloader.OrcLoaderUtils;
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
    /** File paths. */
    private String[] paths;

    /** Cache name. */
    private String cacheName;

    /** Buffer size. */
    private int bufSize;

    /** Load mode. */
    private DirectOrcLoaderMode mode;

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
     * @param paths Paths.
     * @param cacheName Cache name.
     * @param bufSize Buffer size.
     * @param mode Load mode.
     * @param skipCache Skip cache flag.
     */
    public DirectOrcLoaderJob(String[] paths, String cacheName, int bufSize, DirectOrcLoaderMode mode,
        boolean skipCache) {
        this.paths = paths;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.mode = mode;
        this.skipCache = skipCache;
    }

    /** {@inheritDoc} */
    @Override public Object execute() throws IgniteException {
        System.out.println(">>> Starting ORC job: " + this);

        long cnt = 0;

        long startTime = System.currentTimeMillis();

        for (String path : paths) {
            Reader reader = DirectOrcLoaderUtils.readerForPath(path);

            StructObjectInspector inspector = (StructObjectInspector)reader.getObjectInspector();

            switch (mode) {
                case LOCAL_FILES:
                    cnt += readAndLoad(reader, inspector);

                    break;

                case LOCAL_KEYS:
                    cnt += readAndLoadLocalKeys(reader, inspector);

                    break;

                default:
                    throw new IgniteException("Unsupported mode: " + mode);
            }
        }

        long dur = (System.currentTimeMillis() - startTime) / 1_000_000;

        return new DirectOrcLoaderJobResult(this.toString(), ignite.cluster().localNode().id(), cnt, dur);
    }

    /**
     * Read and load keys optionally changing their affinity to match local node.
     *
     * @param reader Reader.
     * @param inspector Inspector.
     * @return Amount of loaded key-value pairs.
     */
    private long readAndLoad(Reader reader, StructObjectInspector inspector) {
        long cnt = 0;

        try (IgniteDataStreamer<Object, CHA> streamer = ignite.dataStreamer(cacheName)) {
            streamer.perNodeBufferSize(bufSize);

            try {
                RecordReader rows = reader.rows();

                OrcStruct row = null;

                while (rows.hasNext()) {
                    row = (OrcStruct) rows.next(row);

                    Object key = OrcLoaderUtils.structToKey(row, inspector);
                    CHA val = OrcLoaderUtils.structToValue(row, inspector);

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
     * Read and load only local keys.
     *
     * @param reader Reader.
     * @param inspector Inspector.
     * @return Amount of loaded key-value pairs.
     */
    private long readAndLoadLocalKeys(Reader reader, StructObjectInspector inspector) {
        long cnt = 0;

        IgniteCache<CHA.Key, CHA> cache = ignite.cache(cacheName);

        Map<CHA.Key, CHA> buf = new HashMap<>(bufSize, 1.0f);

        ClusterNode locNode = ignite.cluster().localNode();

        Affinity<Long> aff = ignite.affinity(cacheName);

        try {
            RecordReader rows = reader.rows();

            OrcStruct row = null;

            while (rows.hasNext()) {
                row = (OrcStruct)rows.next(row);

                CHA.Key key = OrcLoaderUtils.structToKey(row, inspector);

                if (!aff.isPrimary(locNode, key.getSubscriberId()))
                    continue;

                CHA val = OrcLoaderUtils.structToValue(row, inspector);

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
