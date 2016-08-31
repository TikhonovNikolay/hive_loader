package com.atconsulting.ignitehiveloader.direct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hive.ql.io.orc.OrcFile;
import org.apache.hadoop.hive.ql.io.orc.Reader;
import org.apache.ignite.IgniteException;

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
