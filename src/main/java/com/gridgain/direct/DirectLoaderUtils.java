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

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.ignite.IgniteException;

/**
 * Utility methods for direct ORC file loading.
 */
class DirectLoaderUtils {
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
            return fs.listStatus(path);
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

}
