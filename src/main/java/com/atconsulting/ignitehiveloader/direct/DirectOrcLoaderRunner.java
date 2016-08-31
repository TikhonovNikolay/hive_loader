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

    /** Concurrency level. */
    private static final String PROP_CONCURRENCY = "ignite.orc.concurrency";

    /** Affinity mode flag. */
    private static final String PROP_AFF_MODE = "ignite.orc.affinity_mode";

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

        int concurrency = Integer.getInteger(PROP_CONCURRENCY, 0);

        clearCache(cfgPath, cacheName);

        System.out.println(">>> Starting ORC load task [path=" + path + ", cfgPath=" + cfgPath +
            ", cacheName=" + cacheName + ", bufSize=" + bufSize + ", affMode=" + affMode + ']');

        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(cfgPath)) {
            IgniteCompute compute = ignite.compute(ignite.cluster().forDataNodes(cacheName));

            long startTime = System.nanoTime();

            int rows = compute.execute(new DirectOrcLoaderTask(path, cacheName, bufSize, affMode, concurrency), null);

            long dur = (System.nanoTime() - startTime) / 1_000_000;

            System.out.println(">>> Finished ORC load task [dur=" + dur + ", rows=" + rows + ']');
        }
    }

    /**
     * Clear cache before running task.
     *
     * @param cfgPath Path to XML config.
     * @param cacheName Cache name.
     */
    private static void clearCache(String cfgPath, String cacheName) {
        Ignition.setClientMode(true);

        try {
            try (Ignite ignite = Ignition.start(cfgPath)) {
                ignite.cache(cacheName).clear();
            }
        }
        finally {
            Ignition.setClientMode(false);
        }

        System.out.println(">>> Cleared target cache: " + cacheName);
    }
}
