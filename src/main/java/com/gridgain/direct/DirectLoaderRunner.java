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

import com.gridgain.ImportDataSource;
import com.gridgain.LoaderProperties;
import java.util.ArrayList;
import java.util.List;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.compute.ComputeTaskFuture;
import org.apache.ignite.internal.util.typedef.CI1;
import org.apache.ignite.lang.IgniteFuture;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Runner for direct loader.
 */
public class DirectLoaderRunner {
    /**
     * Entry point.
     */
    public static void main(String[] args) {
        String cfgPath = System.getProperty(LoaderProperties.CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalArgumentException("Config path is not specified.");

        int bufSize = Integer.getInteger(LoaderProperties.BUFFER_SIZE, LoaderProperties.DFLT_BUFFER_SIZE);

        if (bufSize <= 0)
            throw new IllegalArgumentException("Buffer size must be positive.");

        int parallelOps = Integer.getInteger(LoaderProperties.PARALLEL_OPS, LoaderProperties.DFLT_PARALLEL_OPS);

        if (parallelOps <= 0)
            throw new IllegalArgumentException("Parallel ops size must be positive.");

        int streamerBatchedParallelOps = Integer.getInteger(LoaderProperties.STREAMER_BATCHED_PARALLEL_OPS,
            LoaderProperties.DFLT_STREAMER_BATCHED_PARALLEL_OPS);

        if (streamerBatchedParallelOps < 1)
            streamerBatchedParallelOps = 1;

        boolean jobPerFile = Boolean.getBoolean(LoaderProperties.JOB_PER_FILE);

        Ignition.setClientMode(true);

        ApplicationContext ctx = new FileSystemXmlApplicationContext(cfgPath);

        List<ImportDataSource> source = (List<ImportDataSource>)ctx.getBean("externalSource", List.class);

        try (Ignite ignite = Ignition.start(cfgPath)) {
            List<ComputeTaskFuture<Integer>> futs = new ArrayList<>();

            for (ImportDataSource src : source) {
                String path = src.getPath();
                final String cacheName = src.getClazz().getSimpleName();

                final DirectLoaderTask task = new DirectLoaderTask(path,
                    src.getClazz(),
                    src.getDelimiter(),
                    bufSize,
                    parallelOps,
                    streamerBatchedParallelOps,
                    jobPerFile,
                    ignite.cluster().localNode());

                System.out.println(">>> Starting load task: " + task);

                IgniteCompute compute = ignite.compute(ignite.cluster().forClients()).withAsync();

                final long startTime = System.nanoTime();

                compute.execute(task, null);

                ComputeTaskFuture<Integer> f = compute.future();

                f.listen(new CI1<IgniteFuture<Integer>>() {
                    @Override public void apply(IgniteFuture<Integer> f) {
                        Integer rows = f.get();

                        long dur = (System.nanoTime() - startTime) / 1_000_000;

                        System.out.println(">>> Finished load task [task=" + task + ", dur=" + dur + ", rows=" + rows + ']');

                        System.out.println(">>> Cache size: " + ignite.cache(cacheName).size());
                    }
                });

                futs.add(f);
            }

            for (ComputeTaskFuture<Integer> fut : futs)
                fut.get();
        }
    }
}
