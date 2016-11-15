/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gridgain.direct;

import java.util.UUID;
import org.apache.ignite.internal.util.typedef.internal.S;

/**
 * Direct ORC loading job result.
 */
public class DirectLoaderJobResult {
    /** Job description. */
    private String job;

    /** Node ID where job was executed. */
    private UUID nodeId;

    /** Amount of keys put to cache. */
    private long cnt;

    /** Total duration in milliseconds. */
    private long dur;

    /**
     * Default constructor.
     */
    public DirectLoaderJobResult() {
        // No-op.
    }

    /**
     * Constructor.
     *
     * @param job Job description.
     * @param nodeId Node ID where job was executed.
     * @param cnt Amount of keys put to cache.
     * @param dur Total duration in milliseconds.
     */
    public DirectLoaderJobResult(String job, UUID nodeId, long cnt, long dur) {
        this.job = job;
        this.nodeId = nodeId;
        this.cnt = cnt;
        this.dur = dur;
    }

    /**
     * @return Job description.
     */
    public String job() {
        return job;
    }

    /**
     * @return Node ID where job was executed.
     */
    public UUID nodeId() {
        return nodeId;
    }

    /**
     * @return Amount of keys put to cache.
     */
    public long count() {
        return cnt;
    }

    /**
     * @return Total duration in milliseconds.
     */
    public long duration() {
        return dur;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DirectLoaderJobResult.class, this);
    }
}
