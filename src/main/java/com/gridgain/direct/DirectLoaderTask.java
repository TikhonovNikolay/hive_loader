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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskAdapter;
import org.apache.ignite.internal.util.tostring.GridToStringExclude;
import org.apache.ignite.internal.util.typedef.F;
import org.apache.ignite.internal.util.typedef.internal.S;
import org.jetbrains.annotations.Nullable;

/**
 * Ignite job to load files from the given directory into Ignite.
 */
public class DirectLoaderTask extends ComputeTaskAdapter<String, Integer> {
    /** Path to files. */
    private final String pathStr;

    /** Cache name. */
    private final Class clazz;

    /** Buffer size. */
    private final int bufSize;

    /** Parallel operations. */
    private final int parallelOps;

    /** Number of parallel operations for batched streamer. */
    private final int streamerBatchedParallelOps;

    /** Whether single job should be created for a file. */
    private final boolean jobPerFile;

    /** File delimiter. */
    private final String delimiter;

    /** Local node. */
    @GridToStringExclude
    private final ClusterNode locNode;

    /**
     * Constructor.
     *
     * @param pathStr Path to files.
     * @param clazz Class.
     * @param bufSize Buffer size.
     * @param parallelOps Parallel operations.
     * @param streamerBatchedParallelOps Number of parallel operations for batched streamer.
     * @param jobPerFile Whether single job should be created for a file.
     * @param locNode Local node.
     */
    public DirectLoaderTask(String pathStr,
        Class clazz,
        String delimiter,
        int bufSize,
        int parallelOps,
        int streamerBatchedParallelOps,
        boolean jobPerFile,
        ClusterNode locNode) {
        this.delimiter = delimiter;
        this.pathStr = pathStr;
        this.clazz = clazz;
        this.bufSize = bufSize;
        this.parallelOps = parallelOps;
        this.streamerBatchedParallelOps = streamerBatchedParallelOps;
        this.jobPerFile = jobPerFile;
        this.locNode = locNode;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("ConstantConditions")
    @Nullable @Override public Map<? extends ComputeJob, ClusterNode> map(
        List<ClusterNode> nodes, @Nullable String arg) throws IgniteException {
        // Exclude local node.
        List<ClusterNode> nodes0 = new ArrayList<>(nodes.size() - 1);

        for (ClusterNode node : nodes) {
            //if (!F.eq(node.id(), locNode.id()))
                nodes0.add(node);
        }

        Map<ComputeJob, ClusterNode> jobs = new HashMap<>();

        Path path = new Path(pathStr);

        FileSystem fs = DirectLoaderUtils.fileSystem(path);

        FileStatus[] files = DirectLoaderUtils.enumerateFiles(fs, path);

        Map<ClusterNode, Integer> nodeCtrs = new HashMap<>();

        for (ClusterNode node : nodes0)
            nodeCtrs.put(node, 0);

        Map<ClusterNode, List<FileStatus>> nodeToFiles = new HashMap<>();

        for (FileStatus file : files) {
            ClusterNode node = nodeForFile(fs, file, nodes0, nodeCtrs);

            List<FileStatus> nodeFiles = nodeToFiles.get(node);

            if (nodeFiles == null) {
                nodeFiles = new ArrayList<>();

                nodeToFiles.put(node, nodeFiles);
            }

            nodeFiles.add(file);

            nodeCtrs.put(node, nodeCtrs.get(node) + 1);
        }

        for (ClusterNode node : nodeToFiles.keySet()) {
            List<FileStatus> nodeFiles = nodeToFiles.get(node);

            if (jobPerFile) {
                for (FileStatus nodeFile : nodeFiles) {
                    ComputeJob job = jobForFiles(Collections.singletonList(nodeFile));

                    jobs.put(job, node);
                }
            }
            else {
                ComputeJob job = jobForFiles(nodeFiles);

                jobs.put(job, node);
            }
        }

        return jobs;
    }

    /** {@inheritDoc} */
    @Nullable @Override public Integer reduce(List<ComputeJobResult> ress) throws IgniteException {
        int total = 0;

        for (ComputeJobResult res : ress) {
            DirectLoaderJobResult res0 = res.getData();

            System.out.println(">>> " + res0);

            total += res0.count();
        }

        return total;
    }

    /**
     * Get best matching node for file.
     *
     * @param fs File system.
     * @param file File.
     * @param nodes Available nodes.
     * @param nodeCtrs Node counters.
     * @return Best matching node
     */
    private ClusterNode nodeForFile(FileSystem fs, FileStatus file, List<ClusterNode> nodes,
        Map<ClusterNode, Integer> nodeCtrs) {
        // Currently we use simplified approach where only first file block is considered.
        BlockLocation block = DirectLoaderUtils.fileLocations(fs, file)[0];

        // Get hosts.
        String[] hosts = DirectLoaderUtils.hostsForBlock(block);

        // Find candidates.
        Collection<ClusterNode> cands = nodesForHosts(nodes, hosts);

        // Get best candidate.
        ClusterNode bestNode;

        if (cands.isEmpty()) {
            bestNode = leastLoadedNode(nodes, nodeCtrs);

            System.out.println(">>> Mapped file to node (random) [file=" + file.getPath() + ", node=" + bestNode.id());
        }
        else {
            bestNode = leastLoadedNode(cands, nodeCtrs);

            System.out.println(">>> Mapped file to node [file=" + file.getPath() + ", node=" + bestNode.id());
        }

        return bestNode;
    }

    /**
     * Get least loaded node.
     *
     * @param cands Candidates.
     * @param nodeCtrs Node counters.
     * @return Cluster node.
     */
    private ClusterNode leastLoadedNode(Collection<ClusterNode> cands, Map<ClusterNode, Integer> nodeCtrs) {
        ClusterNode bestNode = null;
        Integer bestNodeCtr = null;

        for (ClusterNode cand : cands) {
            int candCtr = nodeCtrs.get(cand);

            if (bestNode == null) {
                bestNode = cand;
                bestNodeCtr = candCtr;
            }
            else {
                if (candCtr < bestNodeCtr) {
                    bestNode = cand;
                    bestNodeCtr = candCtr;
                }
            }
        }

        assert bestNode != null;

        return bestNode;
    }

    /**
     * Get nodes located on the given hosts.
     *
     * @param nodes Nodes.
     * @param hosts Hosts.
     * @return Nodes on the igven hosts.
     */
    private Collection<ClusterNode> nodesForHosts(List<ClusterNode> nodes, String[] hosts) {
        Collection<ClusterNode> res = new HashSet<>();

        for (String host : hosts) {
            for (ClusterNode node : nodes) {
                boolean match = false;

                for (String nodeHost : node.hostNames()) {
                    if (F.eq(host, nodeHost)) {
                        match = true;

                        break;
                    }
                }

                if (match)
                    res.add(node);
            }
        }

        return res;
    }

    /**
     * Create job for file.
     *
     * @param files Files.
     * @return Job.
     */
    private DirectLoaderJob jobForFiles(List<FileStatus> files) {
        String[] paths = new String[files.size()];

        for (int i = 0; i < files.size(); i++)
            paths[i] = files.get(i).getPath().toString();

        return new DirectLoaderJob(paths, clazz, delimiter, bufSize, parallelOps, streamerBatchedParallelOps);
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DirectLoaderTask.class, this);
    }
}
