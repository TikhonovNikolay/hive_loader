package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.CHA;
import com.atconsulting.ignitehiveloader.OrcLoaderMode;
import com.atconsulting.ignitehiveloader.OrcLoaderUtils;
import com.atconsulting.ignitehiveloader.filter.OrcLoaderFilter;
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
import org.apache.ignite.lang.IgniteBiTuple;
import org.apache.ignite.resources.IgniteInstanceResource;

import java.util.ArrayList;
import java.util.List;
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

    /** Parallel ops. */
    private int parallelOps;

    /** Load mode. */
    private OrcLoaderMode mode;

    /** Optional filter. */
    private OrcLoaderFilter filter;

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
     * @param filter Optional filter.
     */
    public DirectOrcLoaderJob(String[] paths, String cacheName, int bufSize, int parallelOps, OrcLoaderMode mode,
        OrcLoaderFilter filter) {
        this.paths = paths;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.parallelOps = parallelOps;
        this.mode = mode;
        this.filter = filter;
    }

    /** {@inheritDoc} */
    @Override public Object execute() throws IgniteException {
        System.out.println(">>> Starting ORC job: " + this);

        long cnt = 0;

        long startTime = System.currentTimeMillis();

        try (IgniteDataStreamer<CHA.Key, CHA> streamer = ignite.dataStreamer(cacheName)) {
            streamer.perNodeBufferSize(bufSize);
            streamer.perNodeParallelOperations(parallelOps);

            for (String path : paths) {
                switch (mode) {
                    case SKIP:
                        cnt += readAndLoad(streamer, path, true);

                        break;

                    case STREAMER:
                        cnt += readAndLoad(streamer, path, false);

                        break;

                    case STREAMER_BATCHED:
                        cnt += readAndLoadBatched(streamer, path);

                        break;

                    default:
                        throw new IgniteException("Unsupported mode: " + mode);
                }

                System.out.println(">>> Processed file: " + path);
            }
        }

        long dur = (System.currentTimeMillis() - startTime) / 1_000_000;

        return new DirectOrcLoaderJobResult(this.toString(), ignite.cluster().localNode().id(), cnt, dur);
    }

    /**
     * Read and load keys batching them into separate buffer before passing to streamer.
     *
     * @param streamer Cache streamer.
     * @param path Path to file.
     * @return Amount of loaded key-value pairs.
     */
    private long readAndLoadBatched(IgniteDataStreamer<CHA.Key, CHA> streamer, String path) {
        Reader reader = DirectOrcLoaderUtils.readerForPath(path);

        StructObjectInspector inspector = (StructObjectInspector)reader.getObjectInspector();

        long cnt = 0;

        List<Map.Entry<CHA.Key, CHA>> buf = new ArrayList<>(bufSize);

        try {
            RecordReader rows = reader.rows();

            OrcStruct row = null;

            while (rows.hasNext()) {
                row = (OrcStruct) rows.next(row);

                CHA.Key key = OrcLoaderUtils.structToKey(row, inspector);
                CHA val = OrcLoaderUtils.structToValue(row, inspector);

                if (filter == null || filter.evaluate(key, val)) {
                    buf.add(new IgniteBiTuple<>(key, val));

                    if (buf.size() == bufSize) {
                        streamer.addData(buf);

                        buf = new ArrayList<>(bufSize);
                    }

                    cnt++;
                }
            }

            if (!buf.isEmpty())
                streamer.addData(buf);
        }
        catch (Exception e) {
            throw new IgniteException("Failed to load ORC data to cache.", e);
        }

        return cnt;
    }

    /**
     * Read and load keys.
     *
     * @param streamer Cache streamer.
     * @param path Path to file.
     * @param skip Skip flag.
     * @return Amount of loaded key-value pairs.
     */
    private long readAndLoad(IgniteDataStreamer<CHA.Key, CHA> streamer, String path,
        boolean skip) {
        Reader reader = DirectOrcLoaderUtils.readerForPath(path);

        StructObjectInspector inspector = (StructObjectInspector)reader.getObjectInspector();

        long cnt = 0;

        try {
            RecordReader rows = reader.rows();

            OrcStruct row = null;

            while (rows.hasNext()) {
                row = (OrcStruct) rows.next(row);

                CHA.Key key = OrcLoaderUtils.structToKey(row, inspector);
                CHA val = OrcLoaderUtils.structToValue(row, inspector);

                if (filter == null || filter.evaluate(key, val)) {
                    if (!skip)
                        streamer.addData(key, val);

                    cnt++;
                }
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
