import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * TopologicalSortGraph represents a directed graph used for a topological sort
 * algorithm.
 * 
 * @author Spencer Hubbard
 *
 */
// TODO: document graph algorithm
public class TopologicalSortGraph {
	// Map name of vertex to vertex object.
	private Map<String, Vertex> map;

	/**
	 * Construct graph object.
	 */
	public TopologicalSortGraph() {
		map = new HashMap<String, Vertex>();
	}

	/**
	 * Adds a given (directed) edge to this graph.
	 * 
	 * @param tail
	 * the name of the vertex at the tail of the given edge.
	 * 
	 * @param head
	 * the name of the vertex at the head of the given edge.
	 * 
	 * @throws GraphException
	 * if graph does not contain end-points of edge or edge is a multiple edge.
	 */
	public void addEdge(String tail, String head) {
		checkVertex(tail);
		checkVertex(head);
		if (containsEdge(tail, head)) {
			throw new GraphException("multiple edge");
		}
		Vertex u = map.get(tail);
		Vertex v = map.get(head);
		u.adj.add(v);
		v.deg++;
	}

	/**
	 * Adds a given vertex to this graph.
	 * 
	 * @param name
	 * the name of the given vertex.
	 * 
	 * @exception GraphException
	 * if this graph already contains the given vertex.
	 */
	public void addVertex(String name) {
		if (containsVertex(name)) {
			throw new GraphException();
		}
		// create vertex object
		Vertex v = new Vertex(name);
		// map name to vertex object
		map.put(name, v);
	}

	/**
	 * Checks if this graph contains a given vertex.
	 * 
	 * @param name
	 * the name of the given vertex.
	 * 
	 * @return
	 * <code>true</code> if this graph contains the given edge, otherwise
	 * <code>false</code>.
	 */
	public boolean containsVertex(String name) {
		return map.containsKey(name);
	}

	/**
	 * Checks if this graph contains a given edge.
	 * 
	 * @param tail
	 * the name of the vertex at the tail of the given edge.
	 * 
	 * @param head
	 * the name of the vertex at the head of the given edge.
	 * 
	 * @return
	 * <code>true</code> if this graph contains the given edge, otherwise
	 * <code>false</code>.
	 */
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

	/**
	 * Checks if this graph contains a vertex with in-degree zero. If every
	 * vertex in a graph has positive in-degree, then the graph contains a
	 * cycle. However, the converse is not necessarily true.
	 * 
	 * @return
	 * <code>true</code> if this graph contains a vertex with in-degree zero,
	 * otherwise <code>false</code>.
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
	 * Topological sort algorithm for this graph starting with a given source
	 * vertex. This graph has a topological order if and only if it has no
	 * cycles.
	 * 
	 * @note
	 * The time-complexity is <em>O(m + n)</em> where <em>m</em> is the number
	 * of nodes in this graph and <em>n</em> is the number of edges in this
	 * graph.
	 * 
	 * @note
	 * Uses stack to organize search.
	 * 
	 * @note
	 * Algorithm is similar to DFS.
	 * 
	 * @exception GraphException
	 * if this graph contains a cycle.
	 */
	// TODO: document running time
	public void topologicalSort() {
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
		Queue<Vertex> queue = new LinkedList<Vertex>();
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
			queue.add(u);
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
		debug(queue);
	}

	/* 
	 * Debugging method that prints bookkeeping fields and topological ordering
	 * after topological sort algorithm.
	 */
	private void debug(Queue<Vertex> queue) {
		Vertex v = queue.poll();
		System.out.printf("topological ordering: %s", v.name);
		while (!queue.isEmpty()) {
			v = queue.poll();
			System.out.printf(", %s", v.name);
		}
		System.out.print("\n");
		// TODO: print bookkeeping fields
	}

	// Throw GraphException if graph does not contain given vertex.
	private void checkVertex(String name) throws GraphException {
		if (!containsVertex(name)) {
			throw new GraphException("vertex not found");
		}
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
