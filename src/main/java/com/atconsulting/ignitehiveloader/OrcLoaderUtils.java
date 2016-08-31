package com.atconsulting.ignitehiveloader;

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
import org.apache.hadoop.io.Text;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for ORC loader.
 */
public class OrcLoaderUtils {
    /** Type: long. */
    private static final String TYP_LONG = "long";

    /** Type: timestamp. */
    private static final String TYP_TIMESTAMP = "timestamp";

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

        ArrayList<String> names = new ArrayList<>(OrcCHAField.values().length);

        for (OrcCHAField f: OrcCHAField.values())
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

        inspector = (StructObjectInspector) OrcStruct.createObjectInspector(info);
    }

    /**
     * Get inspector for CHA key-value pair.
     *
     * @return Inspector.
     */
    static StructObjectInspector inspector() {
        return inspector;
    }

    /**
     * Convert ORC structure to key.
     *
     * @param struct Structure.
     * @param inspector Object inspector.
     * @return Key.
     */
    public static CHA.Key structToKey(OrcStruct struct, StructObjectInspector inspector) {
        CHA.Key res = new CHA.Key();

        List<? extends StructField> fields = inspector.getAllStructFieldRefs();

        res.setSubscriberId(longValue(struct, inspector, fields.get(0)));
        res.setStartCallDateTime(timestampValue(struct, inspector, fields.get(1)));

        return res;
    }

    /**
     * Convert ORC structure to key.
     *
     * @param struct Structure.
     * @param inspector Object inspector.
     * @return Key.
     */
    public static CHA structToValue(OrcStruct struct, StructObjectInspector inspector) {
        CHA res = new CHA();

        List<? extends StructField> fields = inspector.getAllStructFieldRefs();

        res.setActivityType(stringValue(struct, inspector, fields.get(2)));
        res.setUsageAmount(longValue(struct, inspector, fields.get(3)));
        res.setBalancesInfo(stringValue(struct, inspector, fields.get(4)));

        return res;
    }

    /**
     * Get long value.
     *
     * @param struct Structure.
     * @param inspector Inspector.
     * @param field Field.
     * @return Value.
     */
    private static long longValue(OrcStruct struct, StructObjectInspector inspector, StructField field) {
        LongWritable data = value(struct, inspector, field);

        return data.get();
    }

    /**
     * Get timestamp value.
     *
     * @param struct Structure.
     * @param inspector Inspector.
     * @param field Field.
     * @return Value.
     */
    private static Timestamp timestampValue(OrcStruct struct, StructObjectInspector inspector, StructField field) {
        TimestampWritable data = value(struct, inspector, field);

        return data.getTimestamp();
    }

    /**
     * Get string value.
     *
     * @param struct Structure.
     * @param inspector Inspector.
     * @param field Field.
     * @return Value.
     */
    private static String stringValue(OrcStruct struct, StructObjectInspector inspector, StructField field) {
        Text data = value(struct, inspector, field);

        return data.toString();
    }

    /**
     * Get field data.
     * @param struct Structure.
     * @param inspector Inspector.
     * @param field Field.
     * @return Field data.
     */
    @SuppressWarnings("unchecked")
    private static <T> T value(OrcStruct struct, StructObjectInspector inspector, StructField field) {
        return (T)inspector.getStructFieldData(struct, field);
    }

    /**
     * Private constructor.
     */
    private OrcLoaderUtils() {
        // No-op.
    }
}
