package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.CHA;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hive.ql.io.orc.OrcFile;
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.ql.io.orc.Reader;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.ignite.IgniteException;

import java.sql.Timestamp;
import java.util.List;

/**
 * Utility methods for direct ORC file loading.
 */
class DirectOrcLoaderUtils {
    /**
     * Get file system. for path.
     *
     * @param path Path.
     * @return File system.
     */
    static FileSystem fileSystem(Path path) {
        try {
            return path.getFileSystem(new Configuration());
        }
        catch (Exception e) {
            throw new IgniteException("Failed to get file system for path: " + path, e);
        }
    }

    /**
     * Enumerate ORC files in the path.
     *
     * @param path Directory path.
     * @return ORC files.
     */
    static FileStatus[] enumerateFiles(FileSystem fs, Path path) {
        try {
            return fs.listStatus(path, new Filter(fs));
        }
        catch (Exception e) {
            throw new IgniteException("Failed to enumerate ORC files in the path: " + path, e);
        }
    }

    /**
     * Get block locations for file.
     *
     * @param fs File system.
     * @param status Status.
     * @return Block locations.
     */
    static BlockLocation[] fileLocations(FileSystem fs, FileStatus status) {
        try {
            return fs.getFileBlockLocations(status, 0, status.getLen());
        }
        catch (Exception e) {
            throw new IgniteException("Failed to get block locations for file: " + status.getPath(), e);
        }
    }

    /**
     * Get hosts for block.
     *
     * @param block Block.
     * @return Hosts.
     */
    static String[] hostsForBlock(BlockLocation block) {
        try {
            return block.getHosts();
        }
        catch (Exception e) {
            throw new IgniteException("Failed to get hosts for block: " + block, e);
        }
    }

    /**
     * Get ORC reader for the given path.
     *
     * @param path Path.
     * @return Reader.
     * @throws IgniteException If failed.
     */
    static Reader readerForPath(String path) throws IgniteException {
        try {
            Path path0 = new Path(path);

            FileSystem fs = path0.getFileSystem(new Configuration());

            return OrcFile.createReader(fs, path0);
        }
        catch (Exception e) {
            throw new IgniteException("Failed to create ORC reader for path: " + path, e);
        }
    }

    /**
     * Convert ORC structure to key.
     *
     * @param struct Structure.
     * @param inspector Object inspector.
     * @return Key.
     */
    static CHA.Key structToKey(OrcStruct struct, StructObjectInspector inspector) {
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
    static CHA structToValue(OrcStruct struct, StructObjectInspector inspector) {
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
     * ORC file filter.
     */
    private static class Filter implements PathFilter {
        /** Target file system. */
        private final FileSystem fs;

        /**
         * Constructor.
         *
         * @param fs File system.
         */
        Filter(FileSystem fs) {
            this.fs = fs;
        }

        /** {@inheritDoc} */
        @Override public boolean accept(Path path) {
            try {
                String name = path.getName();

                boolean filter = name.startsWith("_") || name.startsWith(".") || fs.isDirectory(path);

                return !filter;
            }
            catch (Exception e) {
                throw new IgniteException("Failed to evaluate path against the filter: " + path, e);
            }
        }
    }
}
