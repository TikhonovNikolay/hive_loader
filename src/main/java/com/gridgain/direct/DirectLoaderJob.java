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

import com.gridgain.dw_ua_url;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.internal.util.tostring.GridToStringExclude;
import org.apache.ignite.internal.util.typedef.internal.S;
import org.apache.ignite.lang.IgniteBiTuple;
import org.apache.ignite.lang.IgniteUuid;
import org.apache.ignite.resources.IgniteInstanceResource;

/**
 * Ignite job to load a file into Ignite.
 */
public class DirectLoaderJob implements ComputeJob {
    /** File paths. */
    private String[] paths;

    /** Class. */
    private Class clazz;

    /** Delimiter. */
    private String delimiter;

    /** Buffer size. */
    private int bufSize;

    /** Parallel ops. */
    private int parallelOps;

    /** Number of parallel operations for batched streamer. */
    private int streamerBatchedParallelOps;

    /** Injected Ignite instance. */
    @IgniteInstanceResource
    @GridToStringExclude
    private Ignite ignite;

    /**
     * Constructor.
     */
    public DirectLoaderJob() {
        // No-op.
    }

    /**
     * Constructor.
     *
     * @param paths Paths.
     * @param clazz Class.
     * @param bufSize Buffer size.
     * @param parallelOps Parallel operations.
     */
    public DirectLoaderJob(String[] paths,
        Class clazz,
        String delimiter,
        int bufSize,
        int parallelOps,
        int streamerBatchedParallelOps) {
        this.paths = paths;
        this.clazz = clazz;
        this.delimiter = delimiter;
        this.bufSize = bufSize;
        this.parallelOps = parallelOps;
        this.streamerBatchedParallelOps = streamerBatchedParallelOps;
    }

    /** {@inheritDoc} */
    @Override public Object execute() throws IgniteException {
        System.out.println(">>> Starting job: " + this);

        long cnt;

        long startTime = System.currentTimeMillis();

        cnt = readAndLoadBatched();

        long dur = (System.currentTimeMillis() - startTime) / 1_000_000;

        return new DirectLoaderJobResult(this.toString(), ignite.cluster().localNode().id(), cnt, dur);
    }

    /**
     * Read and load keys batching them into separate buffer before passing to streamer.
     *
     * @return Amount of loaded key-value pairs.
     */
    @SuppressWarnings("unchecked")
    private long readAndLoadBatched() {
        AtomicLong res = new AtomicLong();

        try (IgniteDataStreamer<IgniteUuid, Object> streamer = createStreamer()) {
            // Create queue with tasks.
            ConcurrentLinkedQueue<String> pathsQueue = new ConcurrentLinkedQueue<>();

            Collections.addAll(pathsQueue, paths);

            // Start async threads if needed.
            List<Thread> threads = new ArrayList<>();

            for (int i = 1; i < streamerBatchedParallelOps; i++) {
                Thread thread = new Thread(new BatchLoadTask(streamer, pathsQueue, res));

                thread.start();

                threads.add(thread);
            }

            // Perform sync processing.
            new BatchLoadTask(streamer, pathsQueue, res).run();

            // Await for threads to finish.
            try {
                for (Thread thread : threads)
                    thread.join();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                throw new IgniteException("Failed to wait for async threads to finish.", e);
            }

            // Return.
            return res.get();
        }
    }

    /**
     * Read and load keys batching them into separate buffer before passing to streamer.
     *
     * @param strm Cache streamer.
     * @param path Path to file.
     * @return Amount of loaded key-value pairs.
     */
    private long readAndLoadBatched(IgniteDataStreamer<IgniteUuid, Object> strm, String path) {
        CsvImporter importer = new CsvImporter(path, clazz, delimiter);

        long cnt = 0;

        List<Map.Entry<IgniteUuid, Object>> buf = new ArrayList<>(bufSize);

        try {
            Object o;

            while ((o = importer.readObject()) != null) {
                IgniteUuid key = IgniteUuid.randomUuid();

                buf.add(new IgniteBiTuple<>(key, o));

                if (buf.size() == bufSize) {
                    strm.addData(buf);

                    buf = new ArrayList<>(bufSize);
                }

                cnt++;
            }

            if (!buf.isEmpty())
                strm.addData(buf);
        }
        catch (Exception e) {
            throw new IgniteException("Failed to load data to cache: " + clazz, e);
        }

        return cnt;
    }

    /**
     * Create streamer.
     *
     * @return Streamer.
     */
    private IgniteDataStreamer<IgniteUuid, Object> createStreamer() {
        IgniteDataStreamer<IgniteUuid, Object> streamer = ignite.dataStreamer(clazz.getSimpleName());

        streamer.perNodeBufferSize(bufSize);
        streamer.perNodeParallelOperations(parallelOps);

        return streamer;
    }

    /** {@inheritDoc} */
    @Override public void cancel() {
        // No-op.
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DirectLoaderJob.class, this);
    }

    /**
     * Batch load task.
     */
    private class BatchLoadTask implements Runnable {
        /** Streamer. */
        private final IgniteDataStreamer<IgniteUuid, Object> streamer;

        /** Path to process. */
        private final Queue<String> pathsQueue;

        /** Result holder. */
        private final AtomicLong res;

        /**
         * Constructor.
         *
         * @param streamer Streamer.
         * @param pathsQueue Paths to process.
         * @param res Result holder.
         */
        BatchLoadTask(IgniteDataStreamer<IgniteUuid, Object> streamer, Queue<String> pathsQueue, AtomicLong res) {
            this.streamer = streamer;
            this.pathsQueue = pathsQueue;
            this.res = res;
        }

        /** {@inheritDoc} */
        @Override public void run() {
            long res0 = 0;

            String path;

            while ((path = pathsQueue.poll()) != null)
                res0 += readAndLoadBatched(streamer, path);

            res.addAndGet(res0);
        }
    }
}
