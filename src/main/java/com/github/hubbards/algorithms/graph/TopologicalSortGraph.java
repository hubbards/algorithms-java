package com.github.hubbards.algorithms.graph;

import com.google.common.collect.Lists;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TopologicalSortGraph represents a directed graph with no multiple edges. A
 * topological ordering of the vertices is found using the topological sort
 * algorithm.
 *
 * @author Spencer Hubbard
 */
public class TopologicalSortGraph implements Graph {
    // Map name of vertex to vertex object.
    private Map<String, Vertex> map;

    /**
     * Construct graph object.
     */
    public TopologicalSortGraph() {
        map = new HashMap<String, Vertex>();
    }

    @Override
    public boolean containsVertex(String name) {
        checkNotNull(name);
        return map.containsKey(name);
    }

    @Override
    public boolean containsEdge(String tail, String head) {
        if (containsVertex(tail) && containsVertex(head)) {
            // graph contains end-points
            Vertex u = map.get(tail);
            for (Vertex v : u.adj) {
                if (head.equals(v.name)) {
                    // graph contains edge with tail u and head v
                    return true;
                }
            }
        }
        // graph does not contain edge
        return false;
    }

    @Override
    public void addVertex(String name) {
        if (containsVertex(name)) {
            throw new GraphException("vertex already added");
        }
        Vertex v = new Vertex(name);
        map.put(name, v);
    }

    @Override
    public void addEdge(String tail, String head) {
        if (!containsVertex(tail) || !containsVertex(head)) {
            throw new GraphException("end-point(s) not found");
        }
        if (containsEdge(tail, head)) {
            throw new GraphException("multiple edge");
        }
        Vertex u = map.get(tail);
        Vertex v = map.get(head);
        u.adj.add(v);
        v.deg++;
    }

    /**
     * Checks if this graph contains a vertex with in-degree zero. If every
     * vertex in a graph has positive in-degree, then the graph contains a
     * cycle. However, the converse is not necessarily true.
     *
     * @return <code>true</code> if this graph contains a vertex with in-degree
     * zero, otherwise <code>false</code>.
     */
    public boolean hasInDegreeZero() {
        // find vertex with in-degree zero
        for (Vertex v : map.values()) {
            if (v.deg == 0) {
                // v has in-degree zero
                return true;
            }
        }
        // graph contains a cycle
        return false;
    }

    /**
     * Topological sort algorithm for this graph starting with a given source
     * vertex. This graph has a topological order if and only if it has no
     * cycles.
     * <p>
     * NOTE: The time-complexity is <em>O(m + n)</em> where <em>m</em> is the
     * number of nodes in this graph and <em>n</em> is the number of edges in
     * this graph.
     * <p>
     * NOTE: Uses stack to organize search.
     * <p>
     * NOTE: Algorithm is similar to DFS.
     *
     * @return a topological order of the vertices in this graph.
     * @throws GraphException if this graph contains a cycle.
     */
    public List<String> topologicalSort() {
        /*
         * Pseudocode:
         *
         * Find a node v in graph G with in-degree 0.
         * Order v first.
         * Let G' be the graph obtained by removing v from G.
         * Recursively find a topological order for G'.
         * Append the topological order for G' after v.
         */

        // use stack to organize search
        Stack<Vertex> stack = new Stack<Vertex>();
        // use queue to store topological ordering
        Queue<String> queue = new LinkedList<String>();
        // find vertices with in-degree zero
        for (Vertex v : map.values()) {
            // initialize temp
            v.reset();
            if (v.temp == 0) {
                // v has in-degree zero
                stack.push(v);
            }
        }

        while (!stack.isEmpty()) {
            Vertex u = stack.pop();
            // place u next in topological order
            queue.add(u.name);
            // remove edges with tail u
            for (Vertex v : u.adj) {
                // decrement in-degree of v
                v.temp--;
                if (v.temp == 0) {
                    // v has in-degree zero
                    stack.push(v);
                }
            }
        }

        if (queue.size() != map.values().size()) {
            throw new GraphException("graph contains cycle");
        }

        return Lists.newArrayList(queue);
    }

    /*
     * Vertex represents a vertex of a graph.
     */
    private static class Vertex {
        // Name of this vertex.
        public final String name;
        // Adjacency list for this vertex.
        public List<Vertex> adj;
        // In-degree for this vertex.
        public int deg;

        // Bookkeeping field for topological sort algorithm.
        public int temp;

        // Construct vertex with given name.
        public Vertex(String name) {
            this.name = name;
            adj = new LinkedList<Vertex>();
            deg = 0;
            reset();
        }

        // Set bookkeeping field to default value.
        public void reset() {
            temp = deg;
        }
    }
}
