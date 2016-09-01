package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.CHA;
import com.atconsulting.ignitehiveloader.OrcLoaderMode;
import com.atconsulting.ignitehiveloader.OrcLoaderUtils;
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.ql.io.orc.Reader;
import org.apache.hadoop.hive.ql.io.orc.RecordReader;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.internal.util.tostring.GridToStringExclude;
import org.apache.ignite.internal.util.typedef.internal.S;
import org.apache.ignite.resources.IgniteInstanceResource;

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

    /** Parallel ops. */
    private int parallelOps;

    /** Load mode. */
    private OrcLoaderMode mode;

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
     * @param parallelOps Parallel operations.
     * @param mode Load mode.
     */
    public DirectOrcLoaderJob(String[] paths, String cacheName, int bufSize, int parallelOps, OrcLoaderMode mode) {
        this.paths = paths;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.parallelOps = parallelOps;
        this.mode = mode;
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
                case STREAMER:
                    cnt += readAndLoad(reader, inspector, false);

                    break;

                case SKIP:
                    cnt += readAndLoad(reader, inspector, true);

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
     * @param skip Skip flag.
     * @return Amount of loaded key-value pairs.
     */
    private long readAndLoad(Reader reader, StructObjectInspector inspector, boolean skip) {
        long cnt = 0;

        try (IgniteDataStreamer<Object, CHA> streamer = ignite.dataStreamer(cacheName)) {
            streamer.perNodeBufferSize(bufSize);
            streamer.perNodeParallelOperations(parallelOps);

            try {
                RecordReader rows = reader.rows();

                OrcStruct row = null;

                while (rows.hasNext()) {
                    row = (OrcStruct) rows.next(row);

                    Object key = OrcLoaderUtils.structToKey(row, inspector);
                    CHA val = OrcLoaderUtils.structToValue(row, inspector);

                    if (!skip)
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

    /** {@inheritDoc} */
    @Override public void cancel() {
        // No-op.
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DirectOrcLoaderJob.class, this);
    }
}
