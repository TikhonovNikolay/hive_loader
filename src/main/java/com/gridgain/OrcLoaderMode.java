package com.gridgain;

/**
 * Loader modes enumeration.
 */
public enum OrcLoaderMode {
    /** Use IgniteDataStreamer. */
    STREAMER,

    /** Use IgniteDataStreamer with batched addData() overload. */
    STREAMER_BATCHED,

    /** Use IgniteCache.putAll(). */
    PUT,

    /** Do not load anything to cache. */
    SKIP
}
