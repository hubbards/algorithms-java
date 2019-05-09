package com.github.hubbards.algorithms.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * KruskalGraph represents a weighted, connected, simple graph with positive
 * edge costs. The minimum spanning tree is found using Kruskal's algorithm.
 * <p>
 * TODO: document graph algorithms
 * <p>
 * TODO: add method which returns true if graph is connected, false otherwise
 * <p>
 * TODO: implement interface for graph with minimum spanning tree
 *
 * @author Spencer Hubbard
 */
public class KruskalGraph extends WeightedGraph {
    // Map name of vertex to vertex object.
    private Map<String, Vertex> map;
    // List of edges in this graph.
    private List<Edge> list;

    // Bookkeeping field for number of vertices in this graph.
    private int n;
    // Bookkeeping field for disjoint sets of vertices.
    private int[] array;

    /**
     * Construct graph object.
     */
    public KruskalGraph() {
        map = new HashMap<String, Vertex>();
        n = 0;
        array = null;
        list = new LinkedList<Edge>();
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
            Vertex v = map.get(name2);
            for (Edge e : list) {
                if (u.index == e.tail.index && v.index == e.head.index
                        || u.index == e.head.index && v.index == e.tail.index) {
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
        Vertex v = new Vertex(name, n);
        // map name to vertex object
        map.put(name, v);
        // increment number of vertices
        n++;
    }

    // pre : end-points exist, edge is simple, and cost is positive
    @Override
    public void addWeightedEdge(String name1, String name2, double cost) {
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
     * Finds the minimum spanning tree of this graph using Kruskal's algorithm.
     * <p>
     * NOTE: Uses disjoint sets data structure.
     * <p>
     * TODO: document running time
     *
     * @return cost of minimum spanning tree.
     * @throws GraphException if this graph is not connected.
     */
    public double kruskal() {
        // TODO: check if graph is connected
        // reset bookkeeping fields to default values
        reset();
        // sort edges
        Collections.sort(list);
        double cost = 0;
        // iterate over sorted edges
        for (Edge e : list) {
            int i = find(e.tail.index);
            int j = find(e.head.index);
            if (i != j) {
                // e does not form a cycle with black edges
                union(i, j);
                e.color = Color.BLACK;
                cost += e.cost;
            }
        }
        return cost;
    }

    /*
     * Debugging method that prints bookkeeping fields and cost for each edge
     * in graph after Kruskal's algorithm.
     */
    private void debug() {
        float cost = 0;
        System.out.println("debug output");
        System.out.println("edge:      color: cost:");
        for (Edge e : list) {
            System.out.printf("(%-3.3s, %-3.3s) %-5s  %.2f\n",  e.tail.name, e.head.name, e.color, e.cost);
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
        // reset array for disjoint sets of vertices
        array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = -1;
        }
        // reset bookkeeping field to default value for each edge in graph
        for (Edge e : list) {
            e.reset();
        }
    }

    /*
     * pre : root1 and root2 are legal and distinct root indices
     * post: replaces given sets with their union.
     *
     * NOTE: union-by-rank means make tree with smaller rank subtee of tree
     *       with larger rank
     * NOTE: union-by-rank becomes union-by-height without path compression
     */
    private void union(int root1, int root2) {
        // check preconditions
        checkIndex(root1);
        checkIndex(root2);
        if (root1 == root2 || array[root1] >= 0 || array[root2] >= 0) {
            throw new IllegalArgumentException();
        }
        // form union
        if (array[root2] < array[root1]) {
            // rank of tree with root2 is greater than rank of tree with root1
            array[root1] = root2;
        } else {
            // rank of tree with root1 is greater than or equal to rank of tree
            // with root2
            if (array[root1] == array[root2]) {
                // rank of tree with root1 is equal to rank of tree with root2
                array[root1]--;
            }
            array[root2] = root1;
        }
    }

    /*
     * pre : index is legal
     * post: return root index of given set
     *
     * NOTE: path compression means make root parent of each node on path
     */
    private int find(int index) {
        checkIndex(index);
        // find root
        int root = index;
        while (array[root] >= 0) {
            root = array[root];
        }
        // path compression
        while (index != root) {
            // make root parent of next node on path
            int temp = array[index];
            array[index] = root;
            index = temp;
        }
        return root;
    }

    // Throw IndexOutOfBoundsException if given index is illegal.
    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }

    // Vertex represents a vertex of a graph.
    private static class Vertex {
        // Name of this vertex.
        final String name;
        // Index of this vertex.
        final int index;

        // Construct vertex with given name.
        Vertex(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    // Edge represents a weighted edge of a graph.
    private static class Edge implements Comparable<Edge> {
        // Tail of this edge.
        final Vertex tail;
        // Head of this edge.
        final Vertex head;
        // Cost of this edge.
        final double cost;

        // Bookkeeping field for color of edge.
        Color color;

        // Construct edge with given head and cost.
        Edge(Vertex tail, Vertex head, double cost) {
            this.tail = tail;
            this.head = head;
            this.cost = cost;
            reset();
        }

        // Set bookkeeping field to default value for this edge.
        void reset() {
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
