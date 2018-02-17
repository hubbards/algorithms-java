import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * PrimGraph represents a weighted, connected, simple graph with positive edge
 * costs used for Prim's algorithm.
 * 
 * @author Spencer Hubbard
 *
 */
// TODO: document graph algorithms
// TODO: add method which returns true if graph is connected, false otherwise
// TODO: print some sort of visualization (possibly in driver program for a
// special graph)
public class PrimGraph {
	/**
	 * Color used by Prims's algorithm.
	 * 
	 * @note
	 * We distinguish between white and gray edges to help understand the
	 * algorithm.
	 */
	public static enum Color {
		WHITE, GRAY, BLACK
	}

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

	/**
	 * Adds an edge to this graph with a given head, tail, and cost.
	 * 
	 * @param name1
	 * the name of the vertex at one endpoint of the given edge.
	 * 
	 * @param name2
	 * the name of the vertex at other endpoint of the given edge.
	 * 
	 * @param cost
	 * the cost of the given edge.
	 * 
	 * @exception GraphException
	 * if graph does not contain end-points of edge, edge is not simple, or
	 * cost is non-positive.
	 */
	public void addEdge(String name1, String name2, float cost) {
		// check preconditions
		if (containsEdge(name1, name2)) {
			throw new GraphException("multiple edge");
		}
		if (name1.equals(name2)) {
			throw new GraphException("loop");
		}
		if (cost <= 0) {
			throw new GraphException("non-positive edge cost");
		}
		checkVertex(name1);
		checkVertex(name2);
		// add edge to graph
		Vertex u = map.get(name1);
		Vertex v = map.get(name2);
		Edge e = new Edge(u, v, cost);
		list.add(e);
		u.inc.add(e);
		v.inc.add(e);
	}

	/**
	 * Adds a given vertex to this graph.
	 * 
	 * @param name
	 * the name of the given vertex.
	 * 
	 * @exception GraphException
	 * if this graph contain the given vertex.
	 */
	public void addVertex(String name) {
		// check preconditions
		if (containsVertex(name)) {
			throw new GraphException();
		}
		// create vertex object
		Vertex v = new Vertex(name);
		// map name to vertex object
		map.put(name, v);
		// set source vertex to v
		s = v;
	}

	/**
	 * Checks if this graph contains a given vertex.
	 * 
	 * @param name
	 * the name of the given vertex.
	 * 
	 * @return
	 * <code>true</code> if this graph contains the given vertex, otherwise
	 * <code>false</code>.
	 */
	public boolean containsVertex(String name) {
		return map.containsKey(name);
	}

	/**
	 * Checks if this graph contains a given edge.
	 * 
	 * @param name1
	 * the name of the vertex at one endpoint of the given edge.
	 * 
	 * @param name2
	 * the name of the vertex at the other endpoint of the given edge.
	 * 
	 * @return
	 * <code>true</code> if this graph contains the given edge, otherwise
	 * <code>false</code>.
	 */
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
		System.out.print("\n");
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
			System.out.print("\n");
		}
	}

	/**
	 * Finds the minimum spanning tree of this graph using Prim's algorithm.
	 * 
	 * @note
	 * Algorithm is similar to Dijkstra's algorithm.
	 * 
	 * @note
	 * Use (binary min heap) priority queue to organize search.
	 * 
	 * @exception GraphException
	 * if this graph is empty or not connected.
	 */
	// TODO: comment
	// TODO: document running time
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
	 * 
	 * @note
	 * Algorithm is similar to Dijkstra's algorithm.
	 * 
	 * @note
	 * Uses (pairing heap) priority queue with decrease key operation to
	 * organize search.
	 * 
	 * @exception GraphException
	 * if this graph is empty or not connected.
	 */
	// TODO: comment
	// TODO: document running time
	public void prim2(String name) {
		// TODO: implement
		throw new RuntimeException("method not implemented");
	}

	/* 
	 * Debugging method that prints bookkeeping fields and cost for each edge
	 * in graph after Prim's algorithm.
	 */
	private void debug() {
		float cost = 0;
		System.out.print("debug output\n");
		System.out.printf("edge:      color: cost:\n");
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

	// Throws GraphException if graph does not contain given vertex.
	private void checkVertex(String name) throws GraphException {
		if (!containsVertex(name)) {
			throw new GraphException("vertex not found");
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
