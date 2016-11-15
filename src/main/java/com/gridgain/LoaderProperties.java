package com.gridgain;

import org.apache.ignite.IgniteDataStreamer;

/**
 * ORC loader properties.
 */
public class LoaderProperties {
    /** Output directory. */
    public static final String OUTPUT = "ignite.output";

    /** Property: path to XML configuration. */
    public static final String CONFIG_PATH = "ignite.config_path";

    /** Property: load mode. */
    public static final String MODE = "ignite.mode";

    /** Property: per-node buffer size. */
    public static final String BUFFER_SIZE = "ignite.buffer_size";

    /** Property: per-node parallel operations count. */
    public static final String PARALLEL_OPS = "ignite.parallel_ops";

    /** Whether cache must be cleared. */
    public static final String CLEAR_CACHE = "ignite.clear_cache";

    /** Property: whether separate job should be created for each file. */
    public static final String JOB_PER_FILE = "ignite.job_per_file";

    /** How many threads process files and put data into batched streamer. */
    public static final String STREAMER_BATCHED_PARALLEL_OPS = "ignite.streamer_batched_parallel_ops";

    /** Default value for {@code BUFFER_SIZE}. */
    public static final int DFLT_BUFFER_SIZE = IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE;

    /** Default value for {@code PARALLEL_OPS}. */
    public static final int DFLT_PARALLEL_OPS = IgniteDataStreamer.DFLT_MAX_PARALLEL_OPS;

    /** Default value for {@code STREAMER_BATCHED_PARALLEL_OPS}. */
    public static final int DFLT_STREAMER_BATCHED_PARALLEL_OPS = 4;

    /**
     * Constructor.
     */
    private LoaderProperties() {
        // No-op.
    }
}
