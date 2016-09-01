package com.atconsulting.ignitehiveloader.direct;

import com.atconsulting.ignitehiveloader.OrcLoaderMode;
import com.atconsulting.ignitehiveloader.filter.OrcLoaderSameDayFilter;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Ignite job to load ORC files from the given directory into Ignite.
 */
public class DirectOrcLoaderTask extends ComputeTaskAdapter<String, Integer> {
    /** Path to ORC files. */
    private final String pathStr;

    /** Cache name. */
    private final String cacheName;

    /** Buffer size. */
    private final int bufSize;

    /** Parallel operations. */
    private final int parallelOps;

    /** Load mode. */
    private final OrcLoaderMode mode;

    /** Whether single job should be created for a file. */
    private final boolean jobPerFile;

    /** Whether to load current day only. */
    private final boolean filterCurDay;

    /** Local node. */
    @GridToStringExclude
    private final ClusterNode locNode;

    /**
     * Constructor.
     *
     * @param pathStr Path to ORC files.
     * @param cacheName Cache name.
     * @param bufSize Buffer size.
     * @param parallelOps Parallel operations.
     * @param mode Load mode.
     * @param jobPerFile Whether single job should be created for a file.
     * @param filterCurDay Whether to load current day only.
     * @param locNode Local node.
     */
    public DirectOrcLoaderTask(String pathStr, String cacheName, int bufSize, int parallelOps, OrcLoaderMode mode,
        boolean jobPerFile, boolean filterCurDay, ClusterNode locNode) {
        this.pathStr = pathStr;
        this.cacheName = cacheName;
        this.bufSize = bufSize;
        this.parallelOps = parallelOps;
        this.mode = mode;
        this.jobPerFile = jobPerFile;
        this.filterCurDay = filterCurDay;
        this.locNode = locNode;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("ConstantConditions")
    @Nullable @Override public Map<? extends ComputeJob, ClusterNode> map(
        List<ClusterNode> nodes, @Nullable String arg) throws IgniteException {
        // Exclude local node.
        List<ClusterNode> nodes0 = new ArrayList<>(nodes.size() - 1);

        for (ClusterNode node : nodes) {
            if (!F.eq(node.id(), locNode.id()))
                nodes0.add(node);
        }

        Map<ComputeJob, ClusterNode> jobs = new HashMap<>();

        Path path = new Path(pathStr);

        FileSystem fs = DirectOrcLoaderUtils.fileSystem(path);

        FileStatus[] files = DirectOrcLoaderUtils.enumerateFiles(fs, path);

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
            DirectOrcLoaderJobResult res0 = res.getData();

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
        BlockLocation block = DirectOrcLoaderUtils.fileLocations(fs, file)[0];

        // Get hosts.
        String[] hosts = DirectOrcLoaderUtils.hostsForBlock(block);

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
    private DirectOrcLoaderJob jobForFiles(List<FileStatus> files) {
        String[] paths = new String[files.size()];

        for (int i = 0; i < files.size(); i++)
            paths[i] = files.get(i).getPath().toString();

        return new DirectOrcLoaderJob(paths, cacheName, bufSize, parallelOps, mode,
            filterCurDay ? new OrcLoaderSameDayFilter(new Date()) : null);
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DirectOrcLoaderTask.class, this);
    }
}
