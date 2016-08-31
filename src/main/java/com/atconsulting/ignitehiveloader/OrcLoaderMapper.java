package com.atconsulting.ignitehiveloader;

import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteIllegalStateException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteBiTuple;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * ORC loader mapper.
 */
public class OrcLoaderMapper extends Mapper<NullWritable, OrcStruct,  NullWritable, NullWritable> {
    /** Counter group. */
    private static final String CTR_GRP = "ignite";

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

    /** Ignite instance. */
    private Ignite ignite;

    /** Streamer.*/
    private IgniteDataStreamer<CHA.Key, CHA> streamer;

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

        String cfgPath = context.getConfiguration().get(OrcLoaderProperties.PROP_CONFIG_PATH);

        if (cfgPath == null)
            throw new IllegalStateException("Config property '" + OrcLoaderProperties.PROP_CONFIG_PATH +
                "' must be defined.");

        try {
            ignite = Ignition.ignite();
        }
        catch (IgniteIllegalStateException ignore) {
            Ignition.setClientMode(true);

            ignite = Ignition.start(cfgPath);
        }

        // Prepare streamer.
        String cacheName = context.getConfiguration().get(OrcLoaderProperties.PROP_CACHE_NAME);

        if (cacheName == null)
            throw new IllegalStateException("Config property '" + OrcLoaderProperties.PROP_CACHE_NAME +
                "' must be defined.");

        IgniteCache cache = ignite.getOrCreateCache(cacheName);

        if (cache == null)
            throw new IllegalStateException("Unable to get cache '" + cacheName + "'.");

        int bufSize = context.getConfiguration().getInt(
            OrcLoaderProperties.PROP_STREAMER_BUFFER, IgniteDataStreamer.DFLT_PER_NODE_BUFFER_SIZE);

        streamer = ignite.dataStreamer(cacheName);

        streamer.perNodeBufferSize(bufSize);

        ctrSetupDur = System.nanoTime() - startTime;
    }

    /** {@inheritDoc} */
    @Override public void map(NullWritable key, OrcStruct orcStruct, Context context) throws IOException,
        InterruptedException {
        long mapStartTime = System.nanoTime();

        Map.Entry<CHA.Key, CHA> pair;

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
    private Map.Entry<CHA.Key, CHA> createCha(OrcStruct struct) throws ParseException {
        CHA.Key key = new CHA.Key();

        CHA cha = new CHA();

        for (final StructField field : OrcLoaderUtils.inspector().getAllStructFieldRefs()) {
            final Object data = OrcLoaderUtils.inspector().getStructFieldData(struct, field);

            OrcCHAField field0 = OrcCHAField.valueOf(field.getFieldName());

            switch (field0) {
                case ACTIVITY_TYPE:
                    cha.setActivityType(data.toString());

                    break;

                case BALANCES_INFO:
                    cha.setBalancesInfo(data.toString());

                    break;

                case START_CALL_DATE_TIME:
                    key.setStartCallDateTime(((TimestampWritable)data).getTimestamp());

                    break;

                case SUBSCRIBER_ID:
                    key.setSubscriberId(((LongWritable)data).get());

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

        return new IgniteBiTuple<>(key, cha);
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
