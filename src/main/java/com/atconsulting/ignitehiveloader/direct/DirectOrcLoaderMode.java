package com.atconsulting.ignitehiveloader.direct;

/**
 * Loading mode.
 */
public enum DirectOrcLoaderMode {
    /** Process local files only. */
    LOCAL_FILES,

    /** Process local files only, force all keys to be primary for this node. */
    LOCAL_FILES_FORCE_PRIMARY,

    /** Process local files only, do not load data to cache. */
    LOCAL_FILES_NO_CACHE,

    /** Process local keys only. */
    LOCAL_KEYS,

    /** Process local keys only, do not load data to cache. */
    LOCAL_KEYS_NO_CACHE;
}
