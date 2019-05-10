package com.github.hubbards.algorithms.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * SearchGraph represents a undirected graph with no multiple edge and
 * implements some graph search algorithms.
 * <p>
 * TODO: add method which returns true if graph is connected, false otherwise
 * <p>
 * TODO: add method which returns true if graph is bipartite, false otherwise
 *
 * @author Spencer Hubbard
 */
public class SearchGraph implements Graph {
    // Map name of vertex to vertex object.
    private Map<String, Vertex> map;

    /**
     * Construct graph object.
     */
    public SearchGraph() {
        map = new HashMap<String, Vertex>();
    }

    @Override
    public boolean containsVertex(String name) {
        checkNotNull(name);
        return map.containsKey(name);
    }

    @Override
    public boolean containsEdge(String name1, String name2) {
        if (containsVertex(name1) && containsVertex(name2)) {
            // graph contains end-points
            Vertex u = map.get(name1);
            for (Vertex v : u.adj) {
                if (name2.equals(v.name)) {
                    // v adjacent to u
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addVertex(String name) {
        if (containsVertex(name)) {
            throw new GraphException("vertex already exists");
        }
        // create vertex object
        Vertex v = new Vertex(name);
        // map name to vertex object
        map.put(name, v);
    }

    @Override
    public void addEdge(String name1, String name2) {
        if (!containsVertex(name1) || !containsVertex(name2)) {
            throw new GraphException("end-point(s) not found");
        }
        if (containsEdge(name1, name2)) {
            throw new GraphException("multiple edge");
        }
        Vertex u = map.get(name1);
        Vertex v = map.get(name2);
        u.adj.add(v);
        v.adj.add(u);
    }

    /**
     * Prints an adjacency list representation of this graph to standard output.
     */
    public void printAdjacencyList() {
        System.out.print("vertex: adjacency list:\n");
        for (Vertex u : map.values()) {
            // print vertex v
            System.out.printf("%-3.3s     ", u.name);
            // print adjacency list for vertex v
            Iterator<Vertex> i = u.adj.iterator();
            if (i.hasNext()) {
                Vertex v = i.next();
                System.out.printf("%-3.3s", v.name);
                while (i.hasNext()) {
                    v = i.next();
                    System.out.printf(" --> %-3.3s", v.name);
                }
                System.out.print("\n");
            } else {
                System.out.print("null\n");
            }
        }
    }

    /**
     * Does a breadth-first search (BFS) of this graph starting with a given
     * source vertex.
     * <p>
     * NOTE: Use queue to organize search.
     * <p>
     * NOTE: The time-complexity is <em>O(m + n)</em>, where <em>m</em> is the
     * number of edges and <em>n</em> is the number of vertices.
     * <p>
     * TODO: return a testable result
     *
     * @param name the name of the given source vertex.
     * @throws GraphException if this graph does not contain the given source
     *                        vertex.
     */
    public void breadthFirstSearch(String name) {
        validateContainsVertex(name);
        // initialize bookkeeping fields
        reset();
        // use queue to organize search
        Queue<Vertex> queue = new LinkedList<Vertex>();
        // begin search at source vertex
        Vertex s = map.get(name);
        // explore s
        s.color = Color.GRAY;
        queue.add(s);
        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            // explore all neighbors of u
            for (Vertex v : u.adj) {
                if (v.color == Color.WHITE) {
                    // v unexplored
                    v.color = Color.GRAY;
                    v.dist = u.dist + 1;
                    v.path = u;
                    queue.add(v);
                }
            }
            // u fully explored
            u.color = Color.BLACK;
        }
        debugBFS();
    }

    /**
     * Does iterative depth-first search of this graph starting with a given
     * source vertex.
     * <p>
     * NOTE: Use stack to organize search.
     * <p>
     * NOTE: The time-complexity is <em>O(m + n)</em>, where <em>m</em> is the
     * number of edges and <em>n</em> is the number of vertices.
     * <p>
     * TODO: return a testable result
     *
     * @param name the name of the given source vertex.
     * @throws GraphException if this graph does not contain the given source
     *                        vertex.
     */
    public void depthFirstSearchI(String name) {
        validateContainsVertex(name);
        // initialize bookkeeping fields
        reset();
        // use stack to organize search
        Stack<Vertex> stack = new Stack<Vertex>();
        // begin search at source vertex
        Vertex s = map.get(name);
        stack.push(s);
        while (!stack.isEmpty()) {
            Vertex u = stack.pop();
            if (u.color == Color.WHITE) {
                // explore u
                u.color = Color.GRAY;
                for (Vertex v : u.adj) {
                    if (v.color == Color.WHITE) {
                        // v unexplored
                        v.dist = u.dist + 1;
                        v.path = u;
                        stack.push(v);
                    }
                }
                // u fully explored
                u.color = Color.BLACK;
            }
        }
        debugDFS();
    }

    /**
     * Does recursive depth-first search of this graph starting with a given
     * source vertex.
     * <p>
     * NOTE: Use recursion to organize search.
     * <p>
     * NOTE: The time-complexity is <em>O(m + n)</em>, where <em>m</em> is the
     * number of edges and <em>n</em> is the number of vertices.
     * <p>
     * TODO: return a testable result
     *
     * @param name the name of the given source vertex.
     * @throws GraphException if this graph does not contain the given source
     *                        vertex.
     */
    public void depthFirstSearchR(String name) {
        validateContainsVertex(name);
        // initialize bookkeeping fields
        reset();
        // use recursion to organize search
        // begin search at source vertex
        Vertex s = map.get(name);
        depthFirstSearchR(s, 0);
        debugDFS();
    }

    // Helper method.
    private void depthFirstSearchR(Vertex u, int time) {
        // explore u
        u.color = Color.GRAY;
        time++;
        u.left = time;
        for (Vertex v : u.adj) {
            if (v.color == Color.WHITE) {
                // v unexplored
                v.path = u;
                depthFirstSearchR(v, time);
            }
        }
        // u fully explored
        u.color = Color.BLACK;
        time++;
        u.right = time;
    }

    /*
     * Debugging method that prints bookkeeping fields and path for each vertex
     * in spanning tree after BFS.
     */
    private void debugBFS() {
        System.out.println("debug output");
        System.out.println("vertex: color: distance: path:");
        for (Vertex v : map.values()) {
            System.out.printf("%-3.3s     %-5s  %-3d       %-3.3s",
                    v.name, v.color, v.dist, v.name);
            Vertex u = v.path;
            while (u != null) {
                System.out.printf(" <-- %-3.3s", u.name);
                u = u.path;
            }
            System.out.print("\n");
        }
    }

    /*
     * Debugging method that prints bookkeeping fields and path for each vertex
     * in spanning tree after DFS.
     */
    private void debugDFS() {
        System.out.println("debug output");
        System.out.println("vertex: color: left: right: distance: path:");
        for (Vertex v : map.values()) {
            System.out.printf("%-3.3s     %-5s  %-3d   %-3d    %-3d       %-3.3s",
                    v.name, v.color, v.left, v.right, v.dist, v.name);
            Vertex u = v.path;
            while (u != null) {
                System.out.printf(" <-- %-3.3s", u.name);
                u = u.path;
            }
            System.out.print("\n");
        }
    }

    // Validate this graph has a vertex with the given name
    private void validateContainsVertex(String name) {
        if (!containsVertex(name)) {
            throw new GraphException("vertex not found");
        }
    }

    // Reset bookkeeping fields to default values for each vertex in graph.
    private void reset() {
        for (Vertex v : map.values()) {
            v.reset();
        }
    }

    // Vertex represents a vertex of a graph.
    private static class Vertex {
        // Name of this vertex.
        final String name;
        // Adjacency list for this vertex.
        List<Vertex> adj;

        // Bookkeeping field for recursive DFS.
        int left;
        // Bookkeeping field for recursive DFS.
        int right;
        // Bookkeeping field for length of path to this vertex.
        int dist;
        // Bookkeeping field for previous vertex in path to this vertex.
        Vertex path;
        // Bookkeeping field for color of this vertex.
        Color color;

        // Construct vertex with given name.
        Vertex(String name) {
            this.name = name;
            adj = new LinkedList<Vertex>();
            reset();
        }

        // Set bookkeeping fields to default values for this vertex.
        void reset() {
            left = 0;
            right = 0;
            dist = 0;
            path = null;
            color = Color.WHITE;
        }
    }
}
