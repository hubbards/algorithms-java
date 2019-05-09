package com.github.hubbards.algorithms.graph;

import org.junit.Before;

public abstract class WeightedGraphTest<T extends WeightedGraph> {
    protected static final double DELTA = 0.001;

    protected T graph;

    protected abstract T createGraph();

    @Before
    public void setUp() {
        graph = createGraph();
        // vertices
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");
        graph.addVertex("g");
        // weighted edges
        graph.addWeightedEdge("a", "b", 2);
        graph.addWeightedEdge("a", "c", 4);
        graph.addWeightedEdge("a", "d", 1);
        graph.addWeightedEdge("b", "d", 3);
        graph.addWeightedEdge("b", "e", 10);
        graph.addWeightedEdge("c", "d", 2);
        graph.addWeightedEdge("c", "f", 5);
        graph.addWeightedEdge("d", "e", 7);
        graph.addWeightedEdge("d", "f", 8);
        graph.addWeightedEdge("d", "g", 4);
        graph.addWeightedEdge("e", "g", 6);
        graph.addWeightedEdge("f", "g", 1);
    }
}
