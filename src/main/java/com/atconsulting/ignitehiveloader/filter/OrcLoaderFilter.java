package com.atconsulting.ignitehiveloader.filter;

import com.atconsulting.ignitehiveloader.CHA;

/**
 * Filter for CHA keys.
 */
public interface OrcLoaderFilter {
    /**
     * Evaluate key.
     *
     * @param key Key.
     * @param val Value.
     * @return {@code True} if passed.
     */
    boolean evaluate(CHA.Key key, CHA val);
}
