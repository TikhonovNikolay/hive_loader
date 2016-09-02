package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.OrcLoaderMode;
import com.atconsulting.ignitehiveloader.OrcLoaderProperties;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;

/**
 * Runner for direct ORC loader.
 */
public class DirectOrcLoaderRunner {
    /**
     * Entry point.
     */
    public static void main(String[] args) {
        String path = System.getProperty(OrcLoaderProperties.INPUT);

        if (path == null)
            throw new IllegalArgumentException("Path is not specified.");

        String cfgPath = System.getProperty(OrcLoaderProperties.CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalArgumentException("Config path is not specified.");

        String cacheName = System.getProperty(OrcLoaderProperties.CACHE_NAME);

        int bufSize = Integer.getInteger(OrcLoaderProperties.BUFFER_SIZE, OrcLoaderProperties.DFLT_BUFFER_SIZE);

        if (bufSize <= 0)
            throw new IllegalArgumentException("Buffer size must be positive.");

        int parallelOps = Integer.getInteger(OrcLoaderProperties.PARALLEL_OPS, OrcLoaderProperties.DFLT_PARALLEL_OPS);

        if (parallelOps <= 0)
            throw new IllegalArgumentException("Parallel ops size must be positive.");

        String modeStr = System.getProperty(OrcLoaderProperties.MODE, OrcLoaderProperties.DFLT_MODE.name());

        if (modeStr == null)
            throw new IllegalArgumentException("Mode is not specified.");

        OrcLoaderMode mode = OrcLoaderMode.valueOf(modeStr);

        if (mode == OrcLoaderMode.PUT)
            throw new IllegalArgumentException("Mode is not supported: " + mode);

        int streamerBatchedParallelOps = Integer.getInteger(OrcLoaderProperties.STREAMER_BATCHED_PARALLEL_OPS,
            OrcLoaderProperties.DFLT_STREAMER_BATCHED_PARALLEL_OPS);

        if (streamerBatchedParallelOps < 1)
            streamerBatchedParallelOps = 1;

        boolean jobPerFile = Boolean.getBoolean(OrcLoaderProperties.JOB_PER_FILE);
        boolean filterCurDay = Boolean.getBoolean(OrcLoaderProperties.FILTER_CURRENT_DAY);

        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(cfgPath)) {
            if (Boolean.getBoolean(OrcLoaderProperties.CLEAR_CACHE))
                clearCache(ignite, cacheName);

            DirectOrcLoaderTask task = new DirectOrcLoaderTask(path, cacheName, bufSize, parallelOps, mode,
                streamerBatchedParallelOps, jobPerFile, filterCurDay, ignite.cluster().localNode());

            System.out.println(">>> Starting ORC load task: " + task);

            IgniteCompute compute = ignite.compute(ignite.cluster().forClients());

            long startTime = System.nanoTime();

            int rows = compute.execute(task, null);

            long dur = (System.nanoTime() - startTime) / 1_000_000;

            System.out.println(">>> Finished ORC load task [task=" + task + ", dur=" + dur + ", rows=" + rows + ']');

            System.out.println(">>> Cache size: " + ignite.cache(cacheName).size());
        }
    }

    /**
     * Clear cache before running task.
     *
     * @param ignite Ignite instance.
     * @param cacheName Cache name.
     */
    private static void clearCache(Ignite ignite, String cacheName) {
        ignite.cache(cacheName).clear();

        System.out.println(">>> Cleared cache: " + cacheName);
    }
}
