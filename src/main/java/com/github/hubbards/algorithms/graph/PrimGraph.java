package com.github.hubbards.algorithms.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * PrimGraph represents a weighted, connected, simple graph with positive edge
 * costs. The minimum spanning tree is found using Prim's algorithm.
 * <p>
 * TODO: document graph algorithms
 * <p>
 * TODO: add method which returns true if graph is connected, false otherwise
 * <p>
 * TODO: print some sort of visualization (possibly in driver program for a special graph)
 * <p>
 * TODO: implement interface for graph with minimum spanning tree
 *
 * @author Spencer Hubbard
 */
public class PrimGraph extends WeightedGraph {
    // Map name of vertex to vertex object.
    private Map<String, Vertex> map;
    // List of edges in this graph.
    private List<Edge> list;
    // Source vertex of this graph.
    private Vertex s;

    /**
     * Construct graph object.
     */
    public PrimGraph() {
        map = new HashMap<String, Vertex>();
        list = new LinkedList<Edge>();
        s = null;
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
            for (Edge e : list) {
                if (name1.equals(e.tail.name) && name2.equals(e.head.name)
                        || name1.equals(e.head.name) && name2.equals(e.tail.name)) {
                    // found edge
                    return true;
                }
            }
        }
        // graph does not contain edge
        return false;
    }

    @Override
    public void addVertex(String name) {
        // check preconditions
        if (containsVertex(name)) {
            throw new GraphException("vertex already exists");
        }
        // create vertex object
        Vertex v = new Vertex(name);
        // map name to vertex object
        map.put(name, v);
        // set source vertex to v
        s = v;
    }

    @Override
    public void addWeightedEdge(String name1, String name2, float cost) {
        // check preconditions
        if (!containsVertex(name1) || !containsVertex(name2)) {
            throw new GraphException("end-point(s) not found");
        }
        if (containsEdge(name1, name2)) {
            throw new GraphException("multiple edge");
        }
        if (name1.equals(name2)) {
            throw new GraphException("loop");
        }
        if (cost <= 0) {
            throw new GraphException("non-positive edge cost");
        }
        // add edge to graph
        Vertex u = map.get(name1);
        Vertex v = map.get(name2);
        Edge e = new Edge(u, v, cost);
        list.add(e);
        u.inc.add(e);
        v.inc.add(e);
    }

    /**
     * Prints an adjacency matrix representation of this graph to standard
     * output.
     */
    public void printAdjacencyMatrix() {
        // index vertices of graph
        Vertex[] v = map.values().toArray(new Vertex[0]);
        // print column indices
        System.out.print("   ");
        for (int i = 0; i < v.length; i++) {
            System.out.printf(" %-3.3s", v[i].name);
        }
        System.out.println();
        // print rows of adjacency matrix
        for (int i = 0; i < v.length; i++) {
            // print row index
            System.out.printf("%-3.3s", v[i].name);
            // print row i of adjacency matrix
            for (int j = 0; j < v.length; j++) {
                if (containsEdge(v[i].name, v[j].name)) {
                    // vertex i is adjacent to vertex j
                    System.out.printf(" %-3d", 1);
                } else {
                    // vertex i is not adjacent to vertex j
                    System.out.printf(" %-3d", 0);
                }
            }
            System.out.println();
        }
    }

    /**
     * Finds the minimum spanning tree of this graph using Prim's algorithm.
     * <p>
     * NOTE: Algorithm is similar to Dijkstra's algorithm.
     * <p>
     * NOTE: Use (binary min heap) priority queue to organize search.
     * <p>
     * TODO: document running time
     *
     * @throws GraphException if this graph is empty or not connected.
     */
    public void prim1() {
        // TODO: check graph is connected
        if (s == null) {
            throw new GraphException("empty graph");
        }
        // initialize bookkeeping fields
        reset();
        // use priority queue to organize search
        PriorityQueue<Edge> heap = new PriorityQueue<Edge>();
        // begin search at source vertex
        s.color = Color.BLACK;
        // explore edges incident to s
        for (Edge e : s.inc) {
            // explore e
            e.color = Color.GRAY;
            heap.add(e);
        }
        while (!heap.isEmpty()) {
            Edge e = heap.poll();
            // find end-point of e that is not black
            Vertex u = e.tail;
            if (u.color == Color.BLACK) {
                u = e.head;
            }
            if (u.color == Color.WHITE) {
                // e is minimum cost edge on cut
                u.color = Color.BLACK;
                e.color = Color.BLACK;
                // explore edges incident to u
                for (Edge f : u.inc) {
                    if (f.color == Color.WHITE) {
                        // explore f
                        f.color = Color.GRAY;
                        heap.add(f);
                    }
                }
            }
        }
        debug();
    }

    /**
     * Finds the minimum spanning tree of this graph with a given root node
     * using Prim's algorithm.
     * <p>
     * NOTE: Algorithm is similar to Dijkstra's algorithm.
     * <p>
     * NOTE: Uses (pairing heap) priority queue with decrease key operation to
     * organize search.
     * <p>
     * TODO: document running time
     *
     * @throws GraphException if this graph is empty or not connected.
     */
    public void prim2(String name) {
        // TODO: implement
        throw new RuntimeException("method not implemented");
    }

    // TODO: add getters for spanning tree and cost

    /*
     * Debugging method that prints bookkeeping fields and cost for each edge
     * in graph after Prim's algorithm.
     */
    private void debug() {
        float cost = 0;
        System.out.println("debug output");
        System.out.println("edge:      color: cost:");
        for (Edge e : list) {
            System.out.printf("(%-3.3s, %-3.3s) %-5s  %.2f\n", e.tail.name, e.head.name, e.color, e.cost);
            if (e.color == Color.BLACK) {
                cost += e.cost;
            }
        }
        System.out.printf("total cost: %.2f\n", cost);
    }

    /*
     * Reset bookkeeping fields to default values for each vertex and edge in
     * graph.
     */
    private void reset() {
        // reset bookkeeping field for each vertex
        for (Vertex v : map.values()) {
            v.reset();
        }
        // reset bookkeeping field for each edge
        for (Edge e : list) {
            e.reset();
        }
    }

    // Vertex represents a vertex of a graph.
    private static class Vertex {
        // Name of this vertex.
        public final String name;
        // Incidence list for this vertex.
        public List<Edge> inc;

        // Bookkeeping field for color of this vertex.
        public Color color;

        // Construct vertex with given name.
        public Vertex(String name) {
            this.name = name;
            inc = new LinkedList<Edge>();
            reset();
        }

        // Set bookkeeping field to default value for this vertex.
        public void reset() {
            color = Color.WHITE;
        }
    }

    // Edge represents a weighted edge of a graph.
    private static class Edge implements Comparable<Edge> {
        // Tail of this edge.
        public final Vertex tail;
        // Head of this edge.
        public final Vertex head;
        // Cost of this edge.
        public final float cost;

        // Bookkeeping field for color of edge.
        public Color color;

        // Construct edge with given head and cost.
        public Edge(Vertex tail, Vertex head, float cost) {
            this.tail = tail;
            this.head = head;
            this.cost = cost;
            reset();
        }

        // Set bookkeeping field to default value for this edge.
        public void reset() {
            color = Color.WHITE;
        }

        // Compare cost of this edge with cost of given edge.
        public int compareTo(Edge other) {
            if (cost < other.cost) {
                // cost of this edge is less than other
                return -1;
            } else if (cost > other.cost) {
                // cost of this edge is greater than other
                return 1;
            } else {
                // cost of this edge is equal to other
                return 0;
            }
        }
    }
}
