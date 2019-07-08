package com.github.hubbards.algorithms.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * DijkstraGraph represents a weighted and directed graph with non-negative edge
 * costs. The shortest paths in the graph are found using Dijkstra's algorithm.
 * <p>
 * TODO: add method which returns true if graph is connected, false otherwise
 *
 * @author Spencer Hubbard
 */
public class DijkstraGraph extends WeightedGraph {
    public static final double INFINITY = Double.MAX_VALUE;

    // Map name of vertex to vertex object.
    private Map<String, Vertex> map;

    /**
     * Construct graph object.
     */
    public DijkstraGraph() {
        map = new HashMap<String, Vertex>();
    }

    @Override
    public boolean containsVertex(String name) {
        return map.containsKey(name);
    }

    @Override
    public boolean containsEdge(String tail, String head) {
        if (containsVertex(tail) && containsVertex(head)) {
            // graph contains end-points
            Vertex u = map.get(tail);
            for (Edge e : u.adj) {
                Vertex v = e.head;
                if (head.equals(v.name)) {
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
    }

    // pre : end-points exist, edge is simple, and cost is positive
    @Override
    public void addWeightedEdge(String tail, String head, double cost) {
        // check preconditions
        if (!containsVertex(tail) || !containsVertex(head)) {
            throw new GraphException("end-point(s) not found");
        }
        if (containsEdge(tail, head)) {
            throw new GraphException("multiple edge");
        }
        if (cost < 0) {
            throw new GraphException("negative edge cost");
        }
        // add edge to graph
        Vertex u = map.get(tail);
        Vertex v = map.get(head);
        Edge e = new Edge(v, cost);
        // add e to adjacency list for u
        u.adj.add(e);
    }

    /**
     * Computes the minimum cost of a path in this graph between two given
     * vertices if the given vertices are in the same connected component of
     * this graph.
     *
     * @param name1 the name of the beginning vertex.
     * @param name2 the name of the ending vertex.
     * @return the minimum cost of a path in this graph between two given
     * vertices if the given vertices are in the same connected component of
     * this graph.
     */
    public double minimumCostPath(String name1, String name2) {
        return dijkstra1(name1, name2);
    }

    /*
     * Dijkstra's algorithm computes minimum cost path in this graph from a
     * given source vertex to all other vertexes in the connected component of
     * this graph containing the source vertex.
     *
     * NOTE: Uses (binary min heap) priority queue to organize search.
     * NOTE: Algorithm is similar to BFS.
     *
     * TODO: document running time
     */
    private double dijkstra1(String name1, String name2) {
        // check preconditions
        if (!containsVertex(name1) || !containsVertex(name2)) {
            throw new GraphException("vertex not found");
        }
        // initialize bookkeeping fields
        reset();
        double result = INFINITY;
        // use priority queue to organize search
        PriorityQueue<Path> heap = new PriorityQueue<Path>();
        // begin search at source vertex
        Vertex s = map.get(name1);
        s.cost = 0;
        // explore s
        s.color = Color.GRAY;
        heap.add(new Path(s, 0));
        while (!heap.isEmpty()) {
            Vertex u = heap.poll().last;
            if (u.color != Color.BLACK) {
                for (Edge e : u.adj) {
                    Vertex v = e.head;
                    if (v.color != Color.BLACK) {
                        // explore v
                        v.color = Color.GRAY;
                        double cost = u.cost + e.cost;
                        if (v.cost > cost) {
                            v.cost = cost;
                            v.last = u;
                            heap.add(new Path(v, cost));
                        }
                    }
                }
                // u fully explored
                u.color = Color.BLACK;
                if (u.name.equals(name2)) {
                    result = u.cost;
                }
            }
        }
        return result;
    }

    /*
     * Dijkstra's algorithm computes minimum cost path in this graph from a
     * given source vertex to all other vertexes in the connected component of
     * this graph containing the source vertex.
     *
     * NOTE: Uses (pairing heap) priority queue with decrease key operation to
     *       organize search.
     * NOTE: Algorithm is similar to BFS.
     *
     * TODO: document running time
     */
    private double dijkstra2(String name1, String name2) {
        // TODO: implement
        throw new RuntimeException("method not implemented");
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
        public final String name;
        // Adjacency list for this vertex.
        public List<Edge> adj;
        // TODO: add field for position in pairing heap of path to this vertex for dijkstra2

        // Bookkeeping field for total cost of path to this vertex.
        public double cost;
        // Bookkeeping field for last vertex in path to this vertex.
        public Vertex last;
        // Bookkeeping field for color of this vertex.
        public Color color;

        // Construct vertex with given name.
        public Vertex(String name) {
            this.name = name;
            adj = new LinkedList<Edge>();
            reset();
        }

        // Set bookkeeping fields to default values for this vertex.
        public void reset() {
            cost = INFINITY;
            last = null;
            color = Color.WHITE;
        }
    }

    // Edge represents a weighted and directed edge of a graph.
    private static class Edge {
        // Head of this edge.
        public final Vertex head;
        // Cost of this edge.
        public final double cost;

        // Construct edge with given head and cost.
        public Edge(Vertex head, double cost) {
            this.head = head;
            this.cost = cost;
        }
    }

    /*
     * Path is an element of a priority queue used to organize search for
     * Dijkstra's algorithm.
     */
    private static class Path implements Comparable<Path> {
        // Last vertex in this path.
        public Vertex last;
        // Cost of this path.
        public double cost;

        // Construct path with given last vertex and cost.
        public Path(Vertex last, double cost) {
            this.last = last;
            this.cost = cost;
        }

        // Compare cost of this path with cost of given path.
        public int compareTo(Path other) {
            if (cost < other.cost) {
                // cost of this path is less than other
                return -1;
            } else if (cost > other.cost) {
                // cost of this path is greater than other
                return 1;
            } else {
                // cost of this path is equal to other
                return 0;
            }
        }
    }
}
