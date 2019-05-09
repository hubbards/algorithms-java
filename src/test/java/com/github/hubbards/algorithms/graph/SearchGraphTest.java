package com.github.hubbards.algorithms.graph;

import org.junit.Before;

public class SearchGraphTest {
    private SearchGraph graph;

    @Before
    public void setUp() {
        graph = new SearchGraph();
        // vertices
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");
        graph.addVertex("g");
        graph.addVertex("h");
        // edges
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "e");
        graph.addEdge("c", "e");
        graph.addEdge("c", "g");
        graph.addEdge("c", "h");
        graph.addEdge("d", "e");
        graph.addEdge("e", "f");
        graph.addEdge("g", "h");
    }

    /*
     * TODO: write unit tests
     *
     * BFS from vertex a
     *
     * debug output
     * vertex: color: distance: path:
     * a       BLACK  0         a
     * b       BLACK  1         b   <-- a
     * c       BLACK  1         c   <-- a
     * d       BLACK  2         d   <-- b   <-- a
     * e       BLACK  2         e   <-- b   <-- a
     * f       BLACK  3         f   <-- e   <-- b   <-- a
     * g       BLACK  2         g   <-- c   <-- a
     * h       BLACK  2         h   <-- c   <-- a
     *
     * Iterative DFS from vertex a
     *
     * debug output
     * vertex: color: left: right: distance: path:
     * a       BLACK  0     0      0         a
     * b       BLACK  0     0      4         b   <-- d   <-- e   <-- c   <-- a
     * c       BLACK  0     0      1         c   <-- a
     * d       BLACK  0     0      3         d   <-- e   <-- c   <-- a
     * e       BLACK  0     0      2         e   <-- c   <-- a
     * f       BLACK  0     0      3         f   <-- e   <-- c   <-- a
     * g       BLACK  0     0      3         g   <-- h   <-- c   <-- a
     * h       BLACK  0     0      2         h   <-- c   <-- a
     *
     * Recursive DFS from vertex a
     *
     * debug output
     * vertex: color: left: right: distance: path:
     * a       BLACK  1     2      0         a
     * b       BLACK  2     3      0         b   <-- a
     * c       BLACK  3     4      0         c   <-- b   <-- a
     * d       BLACK  5     6      0         d   <-- e   <-- c   <-- b   <-- a
     * e       BLACK  4     5      0         e   <-- c   <-- b   <-- a
     * f       BLACK  5     6      0         f   <-- e   <-- c   <-- b   <-- a
     * g       BLACK  4     5      0         g   <-- c   <-- b   <-- a
     * h       BLACK  5     6      0         h   <-- g   <-- c   <-- b   <-- a
     */
}
