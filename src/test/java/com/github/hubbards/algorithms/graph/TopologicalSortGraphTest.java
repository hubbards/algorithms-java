package com.github.hubbards.algorithms.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link TopologicalSortGraph}.
 *
 * @author Spencer Hubbard
 */
public class TopologicalSortGraphTest {
    // TODO: write more unit tests

    @Test
    public void testTopologicalSortForComplexGraph() {
        TopologicalSortGraph graph = new TopologicalSortGraph();
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");
        graph.addVertex("g");
        graph.addEdge("a", "d");
        graph.addEdge("a", "e");
        graph.addEdge("a", "g");
        graph.addEdge("b", "c");
        graph.addEdge("b", "e");
        graph.addEdge("b", "f");
        graph.addEdge("c", "d");
        graph.addEdge("c", "e");
        graph.addEdge("d", "e");
        graph.addEdge("e", "f");
        graph.addEdge("e", "g");
        graph.addEdge("f", "g");

        Comparator<String> order = Ordering.explicit(graph.topologicalSort());

        // adjacent vertices
        assertTrue(order.compare("a", "d") < 0);
        assertTrue(order.compare("a", "e") < 0);
        assertTrue(order.compare("a", "g") < 0);
        assertTrue(order.compare("b", "c") < 0);
        assertTrue(order.compare("b", "e") < 0);
        assertTrue(order.compare("b", "f") < 0);
        assertTrue(order.compare("c", "d") < 0);
        assertTrue(order.compare("c", "e") < 0);
        assertTrue(order.compare("d", "e") < 0);
        assertTrue(order.compare("e", "f") < 0);
        assertTrue(order.compare("e", "g") < 0);
        assertTrue(order.compare("f", "g") < 0);

        // path connected vertices (transitivity)
        assertTrue(order.compare("a", "f") < 0); // a < e < f
        assertTrue(order.compare("b", "d") < 0); // b < c < d
        assertTrue(order.compare("b", "g") < 0); // b < e < g
        assertTrue(order.compare("c", "f") < 0); // c < e < f
        assertTrue(order.compare("c", "g") < 0); // c < e < g
        assertTrue(order.compare("d", "f") < 0); // d < e < f
        assertTrue(order.compare("d", "g") < 0); // d < e < g
    }
}
