package com.atconsulting.ignitehiveloader.direct;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;

/**
 * Runner for direct ORC loader.
 */
public class DirectOrcLoaderRunner {
    /** Path to ORC folder. */
    private static final String PROP_PATH = "ignite.orc.path";

    /** Path to XML configuration. */
    private static final String PROP_CFG_PATH = "ignite.orc.config_path";

    /** Cache name. */
    private static final String PROP_CACHE_NAME = "ignite.orc.cache_name";

    /** Buffer size. */
    private static final String PROP_BUF_SIZE = "ignite.orc.buf_size";

    /** Affinity mode flag. */
    private static final String PROP_AFF_MODE = "ignite.orc.affinity_mode";

    /** Skip cache load flag. */
    private static final String PROP_SKIP_CACHE = "ignite.orc.skip_cache";

    /**
     * Entry point.
     */
    public static void main(String[] args) {
        String path = System.getProperty(PROP_PATH);

        if (path == null)
            throw new IllegalArgumentException("Path is not specified.");

        String cfgPath = System.getProperty(PROP_CFG_PATH);

        if (cfgPath == null)
            throw new IllegalArgumentException("Config path is not specified.");

        String cacheName = System.getProperty(PROP_CACHE_NAME);

        int bufSize = Integer.getInteger(PROP_BUF_SIZE, IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE);

        boolean affMode = Boolean.getBoolean(PROP_AFF_MODE);

        boolean skipCache = Boolean.getBoolean(PROP_SKIP_CACHE);

        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(cfgPath)) {
            clearCache(ignite, cacheName);

            System.out.println(">>> Starting ORC load task [path=" + path + ", cfgPath=" + cfgPath +
                ", cacheName=" + cacheName + ", bufSize=" + bufSize + ", affMode=" + affMode + ']');

            IgniteCompute compute = ignite.compute(ignite.cluster().forDataNodes(cacheName));

            long startTime = System.nanoTime();

            DirectOrcLoaderTask task = new DirectOrcLoaderTask(path, cacheName, bufSize, affMode, skipCache);

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
