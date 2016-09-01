package com.atconsulting.ignitehiveloader;

/**
 * ORC loader properties.
 */
public class OrcLoaderProperties {
    /** Path to a directory with ORC files. */
    public static final String INPUT = "ignite.orc.input";

    /** Output directory. */
    public static final String OUTPUT = "ignite.orc.output";

    /** Property: path to XML configuration. */
    public static final String CONFIG_PATH = "ignite.orc.config_path";

    /** Property: cache name. */
    public static final String CACHE_NAME = "ignite.orc.cache_name";

    /** Property: load mode. */
    public static final String MODE = "ignite.orc.mode";

    /** Property: per-node buffer size. */
    public static final String BUFFER_SIZE = "ignite.orc.buffer_size";

    /** Property: per-node parallel operations count. */
    public static final String PARALLEL_OPS = "ignite.orc.parallel_ops";

    /** Whether cache must be cleared. */
    public static final String CLEAR_CACHE = "ignite.orc.clear_cache";

    /** Property: whether only current day events should be loaded. */
    public static final String FILTER_CURRENT_DAY = "ignite.orc.filter.current_day";

    /** Property: whether separate job should be created for each file. */
    public static final String JOB_PER_FILE = "ignite.orc.filter.job_per_file";

    /**
     * Constructor.
     */
    private OrcLoaderProperties() {
        // No-op.
    }
}
