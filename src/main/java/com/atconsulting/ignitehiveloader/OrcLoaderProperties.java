package com.atconsulting.ignitehiveloader;

/**
 * ORC loader properties.
 */
class OrcLoaderProperties {
    /** Path to a directory with ORC files. */
    static final String PROP_INPUT = "ignite.orc.input";

    /** Output directory. */
    static final String PROP_OUTPUT = "ignite.orc.output";

    /** Property: path to XML configuration. */
    static final String PROP_CONFIG_PATH = "ignite.orc.config_path";

    /** Property: cache name. */
    static final String PROP_CACHE_NAME = "ignite.orc.cache_name";

    /** Property: per-node buffer size. */
    static final String PROP_BUFFER_SIZE = "ignite.orc.buffer_size";

    /** Property: per-node parallel operations count. */
    static final String PROP_PARALLEL_OPS = "ignite.orc.parallel_ops";

    /** Property: whether IgniteCache.putAll() should be used instead of streamer. */
    static final String PROP_USE_PUT = "ignite.orc.use_put";

    /** Whether cache must be cleared. */
    static final String PROP_CLEAR_CACHE = "ignite.orc.clear_cache";

    /** Property: when set to {@code true} data will not be loaded to cache. Use to measure Hadoop + ORC overhead. */
    static final String PROP_SKIP_CACHE = "ignite.orc.skip_cache";

    /** Property: whether onl current day events should be loaded. */
    static final String PROP_FILTER_CURRENT_DAY = "ignite.orc.filter.current_day";

    /**
     * Constructor.
     */
    private OrcLoaderProperties() {
        // No-op.
    }
}
