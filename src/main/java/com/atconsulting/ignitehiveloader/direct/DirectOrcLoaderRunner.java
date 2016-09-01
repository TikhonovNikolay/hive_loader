package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.OrcLoaderProperties;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;

/**
 * Runner for direct ORC loader.
 */
public class DirectOrcLoaderRunner {
        /** Load mode. */
    private static final String PROP_MODE = "ignite.orc.mode";

    /** Skip cache flag. */
    private static final String PROP_SKIP_CACHE = "ignite.orc.skip_cache";

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

        int bufSize = Integer.getInteger(OrcLoaderProperties.BUFFER_SIZE, IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE);

        String modeStr = System.getProperty(PROP_MODE);

        if (modeStr == null)
            throw new IllegalArgumentException("Mode is not specified.");

        DirectOrcLoaderMode mode = DirectOrcLoaderMode.valueOf(modeStr);

        boolean skipCache = Boolean.getBoolean(PROP_SKIP_CACHE);

        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(cfgPath)) {
            clearCache(ignite, cacheName);

            DirectOrcLoaderTask task = new DirectOrcLoaderTask(path, cacheName, bufSize, mode, skipCache);

            System.out.println(">>> Starting ORC load task: " + task);

            IgniteCompute compute = ignite.compute(ignite.cluster().forDataNodes(cacheName));

            long startTime = System.nanoTime();

            int rows = compute.execute(task, null);

            long dur = (System.nanoTime() - startTime) / 1_000_000;

            System.out.println(">>> Finished ORC load task [task=" + task + ", dur=" + dur + ", rows=" + rows + ']');
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
