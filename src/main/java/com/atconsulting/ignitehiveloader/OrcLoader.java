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

package com.atconsulting.ignitehiveloader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.io.orc.OrcNewInputFormat;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 * ORC loader.
 */
public class OrcLoader {
    /**
     * Entry point.
     */
    public static void main(String[] args) throws Exception {
        // Prepare configuration.
        final Configuration conf = new Configuration();

        new GenericOptionsParser(conf, args).getRemainingArgs();

        // Get job parameters.
        String input = conf.get(OrcLoaderProperties.INPUT);

        if (input == null)
            throw new IllegalArgumentException("Input path is not specified " +
                "(set " + OrcLoaderProperties.INPUT + " property).");

        String output = conf.get(OrcLoaderProperties.OUTPUT);

        if (output == null)
            throw new IllegalArgumentException("Output path is not specified " +
                "(set " + OrcLoaderProperties.OUTPUT + " property).");

        String cfgPath = conf.get(OrcLoaderProperties.CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalArgumentException("Path to Ignite XML configuration is not specified " +
                "(set " + OrcLoaderProperties.CONFIG_PATH + " property).");

        String cacheName = conf.get(OrcLoaderProperties.CACHE_NAME);

        conf.getEnum(OrcLoaderProperties.MODE, OrcLoaderProperties.DFLT_MODE);

        int bufSize = conf.getInt(OrcLoaderProperties.BUFFER_SIZE, OrcLoaderProperties.DFLT_BUFFER_SIZE);

        if (bufSize <= 0)
            throw new IllegalArgumentException("Buffer size must be positive: " + bufSize);

        int concurrency = conf.getInt(OrcLoaderProperties.PARALLEL_OPS, OrcLoaderProperties.DFLT_PARALLEL_OPS);

        if (concurrency <= 0)
            throw new IllegalArgumentException("Parallel ops must be positive: " + concurrency);

        // Print properties to verify what was passed to runner.
        printProperty(conf, OrcLoaderProperties.INPUT);
        printProperty(conf, OrcLoaderProperties.OUTPUT);
        printProperty(conf, OrcLoaderProperties.CONFIG_PATH);
        printProperty(conf, OrcLoaderProperties.CACHE_NAME);
        printProperty(conf, OrcLoaderProperties.MODE);
        printProperty(conf, OrcLoaderProperties.BUFFER_SIZE);
        printProperty(conf, OrcLoaderProperties.PARALLEL_OPS);
        printProperty(conf, OrcLoaderProperties.CLEAR_CACHE);
        printProperty(conf, OrcLoaderProperties.FILTER_CURRENT_DAY);

        // Clear cache if needed.
        if (conf.getBoolean(OrcLoaderProperties.CLEAR_CACHE, false))
            clearCache(cfgPath, cacheName);

        // Prepare job.
        final Job job = Job.getInstance(conf, "Ignite ORC Loader");

        job.setJarByClass(OrcLoader.class);
        job.setInputFormatClass(OrcNewInputFormat.class);
        job.setMapperClass(OrcLoaderMapper.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        // Submit the job.
        long startTime = System.nanoTime();

        boolean res = job.waitForCompletion(true);

        long dur = (System.nanoTime() - startTime) / 1_000_000;

        System.out.println(">>> Total duration: " + dur);

        System.exit(res ? 0 : 1);
    }

    /**
     * Clear cache using provided configuration.
     *
     * @param cfgPath Path to Ignite XML configuration file.
     * @param cacheName Cache name.
     */
    private static void clearCache(String cfgPath, String cacheName) {
        boolean oldCliMode = Ignition.isClientMode();

        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(cfgPath)) {
            ignite.cache(cacheName).clear();
        }
        finally {
            Ignition.setClientMode(oldCliMode);
        }
    }

    /**
     * Print configuration property to the console.
     *
     * @param conf Configuration.
     * @param key Key.
     */
    private static void printProperty(Configuration conf, String key) {
        System.out.println("Conf [" + key + "] = [" + conf.get(key) + ']');
    }
}
