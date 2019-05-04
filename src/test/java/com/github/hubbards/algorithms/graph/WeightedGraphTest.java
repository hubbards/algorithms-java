package com.github.hubbards.algorithms.graph;

import org.junit.Before;

public abstract class WeightedGraphTest<T extends WeightedGraph> {
    protected T graph;

    protected abstract T createGraph();

    @Before
    public void setUp() {
        graph = createGraph();
        graph.addVertex("a"); // a or 1
        graph.addVertex("b"); // b or 2
        graph.addVertex("c"); // c or 3
        graph.addVertex("d"); // d or 4
        graph.addVertex("e"); // e or 5
        graph.addVertex("f"); // f or 6
        graph.addVertex("g"); // g or 7
        graph.addWeightedEdge("a", "b", 2); // (a, b, 2) or (1, 2, 2)
        graph.addWeightedEdge("a", "c", 4); // (a, c, 1) or (1, 3, 4)
        graph.addWeightedEdge("a", "d", 1); // (a, d, 1) or (1, 4, 1)
        graph.addWeightedEdge("b", "d", 3); // (b, d, 3) or (2, 4, 3)
        graph.addWeightedEdge("b", "e", 10); // (b, e, 10) or (2, 5, 10)
        graph.addWeightedEdge("c", "d", 2); // (c, d, 2) or (3, 4, 2)
        graph.addWeightedEdge("c", "f", 5); // (c, f, 5) or (3, 6, 5)
        graph.addWeightedEdge("d", "e", 7); // (d, e, 7) or (4, 5, 7)
        graph.addWeightedEdge("d", "f", 8); // (d, f, 8) or (4, 6, 8)
        graph.addWeightedEdge("d", "g", 4); // (d, g, 4) or (4, 7, 4)
        graph.addWeightedEdge("e", "g", 6); // (e, g, 6) or (5, 7, 6)
        graph.addWeightedEdge("f", "g", 1); // (f, g, 1) or (6, 7, 1)
    }
}
