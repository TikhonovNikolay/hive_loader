package com.atconsulting.ignitehiveloader;

import org.apache.ignite.IgniteDataStreamer;

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
    public static final String JOB_PER_FILE = "ignite.orc.job_per_file";

    /** How many threads process files and put data into batched streamer. */
    public static final String STREAMER_BATCHED_PARALLEL_OPS = "ignite.orc.streamer_batched_parallel_ops";

    /** Default value for {@code MODE}. */
    public static final OrcLoaderMode DFLT_MODE = OrcLoaderMode.STREAMER_BATCHED;

    /** Default value for {@code BUFFER_SIZE}. */
    public static final int DFLT_BUFFER_SIZE = IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE;

    /** Default value for {@code PARALLEL_OPS}. */
    public static final int DFLT_PARALLEL_OPS = IgniteDataStreamer.DFLT_MAX_PARALLEL_OPS;

    /** Default value for {@code STREAMER_BATCHED_PARALLEL_OPS}. */
    public static final int DFLT_STREAMER_BATCHED_PARALLEL_OPS = 4;

    /**
     * Constructor.
     */
    private OrcLoaderProperties() {
        // No-op.
    }
}
