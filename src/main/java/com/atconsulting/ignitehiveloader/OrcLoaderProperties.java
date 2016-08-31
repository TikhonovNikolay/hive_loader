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

    /** Whether cache must be cleared. */
    static final String PROP_CLEAR_CACHE = "ignite.orc.clear_cache";

    /** Property: streamer buffer size. */
    static final String PROP_BUFFER_SIZE = "ignite.orc.buffer_size";

    /** Property: concurrency. */
    static final String PROP_CONCURRENCY = "ignite.orc.concurrency";

    /** Property: whether onl current day events should be loaded. */
    static final String PROP_FILTER_CURRENT_DAY = "ignite.orc.filter.current_day";

    /**
     * Constructor.
     */
    private OrcLoaderProperties() {
        // No-op.
    }
}
