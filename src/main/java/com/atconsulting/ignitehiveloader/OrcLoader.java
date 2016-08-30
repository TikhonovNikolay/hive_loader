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
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.VarcharTypeInfo;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteIllegalStateException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgniteBiTuple;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ORC loader.
 */
public class OrcLoader {
    /** Property: path to XML configuration. */
    private static final String PROP_CONFIG_PATH = "ignite.config.path";

    /** Property: cache name. */
    private static final String PROP_CACHE_NAME = "ignite.cache.name";

    /** Property: streamer buffer size. */
    private static final String PROP_STREAMER_BUFFER = "ignite.streamer.buffer";

    /** Property: whether to use alternative affinity. */
    private static final String PROP_AFFINITY = "ignite.affinity";

    /** Counter group. */
    private static final String CTR_GRP = "ignite";

    /** Type: long. */
    private static final String TYP_LONG = "long";

    /** Type: timestamp. */
    private static final String TYP_TIMESTAMP = "timestamp";

    /** Counter: total duration. */
    private static final String CTR_TOTAL_DUR = "dur.total";

    /** Counter: setup duration. */
    private static final String CTR_SETUP_DUR = "dur.setup";

    /** Counter: cleanup duration. */
    private static final String CTR_CLEANUP_DUR = "dur.cleanup";

    /** Counter: map count. */
    private static final String CTR_MAP_CNT = "cnt.map";

    /** Counter: map duration in milliseconds. */
    private static final String CTR_MAP_DUR = "dur.map";

    /** Counter: streamer add duration.. */
    private static final String CTR_ADD_DUR = "dur.add";

    /** Hive object inspector. */
    private static final StructObjectInspector inspector;

    static {
        // ----------------------------------
        // The table ddl:
        //        CREATE TABLE CHA_MIN
        //            (
        //                SUBSCRIBER_ID         BIGINT,
        //                START_CALL_DATE_TIME  TIMESTAMP,
        //                ACTIVITY_TYPE         STRING,
        //                USAGE_AMOUNT          BIGINT,
        //                BALANCES_INFO         STRING
        //            )
        //        STORED AS ORC;
        // -----------------------------------

        StructTypeInfo info = new StructTypeInfo();

        ArrayList<String> names = new ArrayList<>(ChaField.values().length);

        for (ChaField f: ChaField.values())
            names.add(f.name());

        info.setAllStructFieldNames(names);

        final ArrayList<TypeInfo> infos = new ArrayList<>(names.size());

        PrimitiveTypeInfo typLong = new PrimitiveTypeInfo() {
            @Override public PrimitiveObjectInspector.PrimitiveCategory getPrimitiveCategory() {
                return PrimitiveObjectInspector.PrimitiveCategory.LONG;
            }
        };
        typLong.setTypeName(TYP_LONG);

        PrimitiveTypeInfo typTimestamp = new PrimitiveTypeInfo() {
            @Override public PrimitiveObjectInspector.PrimitiveCategory getPrimitiveCategory() {
                return PrimitiveObjectInspector.PrimitiveCategory.TIMESTAMP;
            }
        };
        typTimestamp.setTypeName(TYP_TIMESTAMP);

        PrimitiveTypeInfo typString = new VarcharTypeInfo();

        infos.add(typLong);
        infos.add(typTimestamp);
        infos.add(typString);
        infos.add(typLong);
        infos.add(typString);

        info.setAllStructFieldTypeInfos(infos);

        inspector = (StructObjectInspector)OrcStruct.createObjectInspector(info);
    }

    /**
     * Mapper.
     */
    public static class OrcFetcherMapper extends Mapper<NullWritable, OrcStruct,  NullWritable, NullWritable> {
        /** Ignite instance. */
        private Ignite ignite;

        /** Streamer.*/
        private IgniteDataStreamer<Object, CHA> streamer;

        /** Buffer size. */
        private int bufSize;

        /** Pre-calculated affinity key. */
        private int affKey = -1;

        /** Near data node. */
        private ClusterNode nearNode;

        /** Unique counter ID. */
        private String ctrId;

        /** Counter: start time. */
        private long startTime;

        /** Setup duration. */
        private long ctrSetupDur;

        /** Counter: map duration. */
        private long ctrMapDur;

        /** Counter: map count. */
        private long ctrMapCnt;

        /** Counter: duration of streamer add operations. */
        private long ctrAddDur;

        /** {@inheritDoc} */
        @SuppressWarnings("unchecked")
        @Override protected void setup(Context context) throws IOException, InterruptedException {
            ctrId = Integer.toString(context.getTaskAttemptID().getTaskID().getId());

            startTime = System.currentTimeMillis();

            String cfgPath = context.getConfiguration().get(PROP_CONFIG_PATH);

            if (cfgPath == null)
                throw new IllegalStateException("Config property '" + PROP_CONFIG_PATH + "' must be defined.");

            try {
                ignite = Ignition.ignite();
            }
            catch (IgniteIllegalStateException ignore) {
                Ignition.setClientMode(true);

                ignite = Ignition.start(cfgPath);
            }

            // Prepare streamer.
            String cacheName = context.getConfiguration().get(PROP_CACHE_NAME);

            if (cacheName == null)
                throw new IllegalStateException("Config property '" + PROP_CACHE_NAME + "' must be defined.");

            IgniteCache cache = ignite.getOrCreateCache(cacheName);

            if (cache == null)
                throw new IllegalStateException("Unable to get cache '" + cacheName + "'.");

            bufSize = context.getConfiguration().getInt(
                PROP_STREAMER_BUFFER, IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE);

            streamer = ignite.dataStreamer(cacheName);

            streamer.perNodeBufferSize(bufSize);

            if (context.getConfiguration().getBoolean(PROP_AFFINITY, false)) {
                Collection<String> locNodeAddrs = ignite.cluster().localNode().addresses();

                for (ClusterNode dataNode : ignite.cluster().forDataNodes(cacheName).nodes()) {
                    for (String dataNodeAddr : dataNode.addresses()) {
                        // Ignore loopback.
                        if ("127.0.0.1".equals(dataNodeAddr) || "localhost".equals(dataNodeAddr))
                            continue;

                        if (locNodeAddrs.contains(dataNodeAddr)) {
                            nearNode = dataNode;

                            break;
                        }
                    }
                }

                Affinity aff = ignite.affinity(cacheName);

                affKey = 0;

                while (true) {
                    if (aff.isPrimary(nearNode, aff))
                        break;

                    affKey++;
                }
            }

            ctrSetupDur = System.nanoTime() - startTime;
        }

        /** {@inheritDoc} */
        @Override public void map(NullWritable key, OrcStruct orcStruct, Context context) throws IOException,
            InterruptedException {
            long mapStartTime = System.nanoTime();

            Map.Entry<Object, CHA> pair;

            try {
                pair = createCha(orcStruct);
            }
            catch (ParseException pe) {
                throw new IOException(pe);
            }

            long addStartTime = System.nanoTime();

            streamer.addData(pair);

            long mapStopTime = System.nanoTime();

            // Update counters.
            ctrAddDur += mapStopTime - addStartTime;
            ctrMapDur += mapStopTime - mapStartTime;
            ctrMapCnt++;
        }

        /** {@inheritDoc} */
        @Override protected void cleanup(Context ctx) throws IOException, InterruptedException {
            long cleanupStartTime = System.nanoTime();

            if (streamer != null) {
                streamer.flush();
                streamer.close();
            }

            ctrAddDur += System.nanoTime() - cleanupStartTime;

            // Close the client node.
            if (ignite != null)
                ignite.close();

            long stopTime = System.nanoTime();

            counter(ctx, CTR_SETUP_DUR).setValue(ctrSetupDur / 1_000_000);
            counter(ctx, CTR_CLEANUP_DUR).setValue((stopTime - cleanupStartTime) / 1_000_000);
            counter(ctx, CTR_MAP_DUR).setValue(ctrMapDur / 1_000_000);
            counter(ctx, CTR_TOTAL_DUR).setValue((stopTime - startTime) / 1_000_000);
            counter(ctx, CTR_ADD_DUR).setValue(ctrAddDur / 1_000_000);
            counter(ctx, CTR_MAP_CNT).setValue(ctrMapCnt);
        }

        /**
         * Create CHA structure.
         *
         * @param struct Structure.
         * @return Pair.
         * @throws ParseException If failed.
         */
        @SuppressWarnings("ConstantConditions")
        private Map.Entry<Object, CHA> createCha(OrcStruct struct) throws ParseException {
            Object key = customAffinity() ? new CHA.Key2() : new CHA.Key();

            CHA cha = new CHA();

            for (final StructField field : inspector.getAllStructFieldRefs()) {
                final Object data = inspector.getStructFieldData(struct, field);

                ChaField field0 = ChaField.valueOf(field.getFieldName());

                switch (field0) {
                    case ACTIVITY_TYPE:
                        cha.setActivityType(data.toString());

                        break;

                    case BALANCES_INFO:
                        cha.setBalancesInfo(data.toString());

                        break;

                    case START_CALL_DATE_TIME:
                        if (customAffinity())
                            ((CHA.Key2)key).setStartCallDateTime(((TimestampWritable)data).getTimestamp());
                        else
                            ((CHA.Key)key).setStartCallDateTime(((TimestampWritable)data).getTimestamp());

                        break;

                    case SUBSCRIBER_ID:
                        if (customAffinity())
                            ((CHA.Key2)key).setSubscriberId(((LongWritable)data).get());
                        else
                            ((CHA.Key)key).setSubscriberId(((LongWritable)data).get());

                        break;

                    case USAGE_AMOUNT:
                        cha.setUsageAmount(((LongWritable)data).get());

                        break;

                    default:
                        throw new Error("Unknown type: " + field0);
                }

                // These fields are not present in the table:
                cha.setLoadDuration(-1);
                cha.setPutTimestamp(null);
            }

            if (customAffinity())
                ((CHA.Key2)key).aff = affKey;

            return new IgniteBiTuple<>(key, cha);
        }

        /**
         * @return Whether custom affinity should be used.
         */
        private boolean customAffinity() {
            return affKey != -1;
        }

        /**
         * Create counter.
         *
         * @param name Counter name.
         * @return Counter.
         */
        private Counter counter(Context ctx, String name) {
            String name0 = ctrId + "." + name;

            return ctx.getCounter(CTR_GRP, name0);
        }
    }

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

        job.setMapperClass(OrcFetcherMapper.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        for (int i = 0; i < otherArgs.length - 1; ++i)
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));

        // Submit the job and wait it to complete:
        boolean ok = job.waitForCompletion(true);

        System.exit(ok ? 0 : 1);
    }

    private static void clearCache(Configuration cfg) {
        String cfgPath = cfg.get(PROP_CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalStateException("Config property '" + PROP_CONFIG_PATH + "' must be defined.");

        Ignition.setClientMode(true);

        Ignite ignite = Ignition.start(cfgPath);

        // Prepare streamer.
        String cacheName = cfg.get(PROP_CACHE_NAME);

        if (cacheName == null)
            throw new IllegalStateException("Config property '" + PROP_CACHE_NAME + "' must be defined.");

        IgniteCache cache = ignite.getOrCreateCache(cacheName);

        cache.clear();

        ignite.close();
    }

    /**
     * Table fields.
     */
    private enum ChaField {
        SUBSCRIBER_ID,
        START_CALL_DATE_TIME,
        ACTIVITY_TYPE,
        USAGE_AMOUNT,
        BALANCES_INFO,
    }
}
