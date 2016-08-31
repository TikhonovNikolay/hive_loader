package com.atconsulting.ignitehiveloader;

/**
 * ORC loader properties.
 */
public class OrcLoaderProperties {
    /** Property: path to XML configuration. */
    public static final String PROP_CONFIG_PATH = "ignite.config.path";

    /** Property: cache name. */
    public static final String PROP_CACHE_NAME = "ignite.cache.name";

    /** Property: streamer buffer size. */
    public static final String PROP_STREAMER_BUFFER = "ignite.streamer.buffer";

    /**
     * Constructor.
     */
    private OrcLoaderProperties() {
        // No-op.
    }
}
