package com.atconsulting.ignitehiveloader;

import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.VarcharTypeInfo;

import java.util.ArrayList;

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
     * Private constructor.
     */
    private OrcLoaderUtils() {
        // No-op.
    }
}
