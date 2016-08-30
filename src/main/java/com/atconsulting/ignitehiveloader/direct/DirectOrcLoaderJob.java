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
import org.apache.ignite.resources.IgniteInstanceResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Ignite job to load a file into Ignite.
 */
public class DirectOrcLoaderJob implements ComputeJob {
    /** Injected Ignite instance. */
    @IgniteInstanceResource
    private Ignite ignite;

    /** Path to file. */
    private String path;

    /** Cache name. */
    private String cacheName;

    /** Buffer size. */
    private int bufSize;

    /** Affinity mode flag. */
    private boolean affMode;

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
     */
    public DirectOrcLoaderJob(String path, String cacheName, int bufSize, boolean affMode) {
        this.path = path;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.affMode = affMode;
    }



    /** {@inheritDoc} */
    @Override public Object execute() throws IgniteException {
        int rowCnt = 0;

        Reader reader = DirectOrcLoaderUtils.readerForPath(path);

        StructObjectInspector inspector = (StructObjectInspector)reader.getObjectInspector();

        if (affMode)
            return loadWithAffinityMode(reader, inspector);

        try (IgniteDataStreamer<CHA.Key, CHA> streamer = ignite.dataStreamer(cacheName)) {
            streamer.perNodeBufferSize(bufSize);

            try {
                RecordReader rows = reader.rows();

                OrcStruct row = null;

                while (rows.hasNext()) {
                    row = (OrcStruct)rows.next(row);

                    CHA.Key key = DirectOrcLoaderUtils.structToKey(row, inspector);
                    CHA val = DirectOrcLoaderUtils.structToValue(row, inspector);

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
    private int loadWithAffinityMode(Reader reader, StructObjectInspector inspector) {
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
                    cache.putAll(buf);

                    buf = new HashMap<>(bufSize, 1.0f);
                }

                rowCnt++;
            }

            if (buf.size() > 0)
                cache.putAll(buf);
        }
        catch (Exception e) {
            throw new IgniteException("Failed to load ORC data to cache.", e);
        }

        return rowCnt;
    }

    /** {@inheritDoc} */
    @Override public void cancel() {
        // No-op.
    }
}
