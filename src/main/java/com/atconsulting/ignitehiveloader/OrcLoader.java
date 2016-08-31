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
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

/**
 * ORC loader.
 */
public class OrcLoader {
    /**
     * Except the generic arguments the following args are expected: <in_dir> [<in_dir>...] <out_dir>.  
     *
     * @param args The program arguments.
     * @throws Exception On error.
     */
    public static void main(String[] args) throws Exception {
        final Configuration conf = new Configuration();

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        if (otherArgs.length < 1) {
            System.err.println("Parameters: <in> [<in>...] <out>");

            System.exit(2);
        }

        clearCache(conf);

        final Job job = Job.getInstance(conf, "Ignite ORC Loader");

        job.setJarByClass(OrcLoader.class);

        job.setInputFormatClass(OrcNewInputFormat.class);

        job.setMapperClass(OrcLoaderMapper.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        for (int i = 0; i < otherArgs.length - 1; ++i)
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));

        // Submit the job and wait it to complete:
        boolean ok = job.waitForCompletion(true);

        System.exit(ok ? 0 : 1);
    }

    /**
     * Clear cache using provided configuration.
     *
     * @param cfg Configuration.
     */
    private static void clearCache(Configuration cfg) {
        String cfgPath = cfg.get(OrcLoaderProperties.PROP_CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalStateException("Config property '" + OrcLoaderProperties.PROP_CONFIG_PATH +
                "' must be defined.");

        Ignition.setClientMode(true);

        Ignite ignite = Ignition.start(cfgPath);

        // Prepare streamer.
        String cacheName = cfg.get(OrcLoaderProperties.PROP_CACHE_NAME);

        if (cacheName == null)
            throw new IllegalStateException("Config property '" + OrcLoaderProperties.PROP_CACHE_NAME +
                "' must be defined.");

        IgniteCache cache = ignite.getOrCreateCache(cacheName);

        cache.clear();

        ignite.close();
    }
}
