package com.github.hubbards.algorithms.graph;

public class DijkstraGraphTest extends WeightedGraphTest<DijkstraGraph> {
    @Override
    protected DijkstraGraph createGraph() {
        return new DijkstraGraph();
    }

    /*
     * TODO: write unit test
     *
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
}
