package com.github.hubbards.algorithms.graph;

import org.junit.Assert;
import org.junit.Test;

public class DijkstraGraphTest extends WeightedGraphTest<DijkstraGraph> {
    @Override
    protected DijkstraGraph createGraph() {
        return new DijkstraGraph();
    }

    /*
     * Shortest paths in graph from vertex a
     *
     * debug output
     * vertex: color: path:
     * a       BLACK  a
     * b       BLACK  b   <-- a
     * c       BLACK  c   <-- a
     * d       BLACK  d   <-- a
     * e       BLACK  e   <-- d   <-- a
     * f       BLACK  f   <-- d   <-- a
     * g       BLACK  g   <-- d   <-- a
     *
     */

    @Test
    public void testMinimumCostPath() {
        Assert.assertEquals(5, graph.minimumCostPath("a", "g"), DELTA);
    }

    // TODO: add more tests
}
