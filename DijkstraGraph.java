import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * DijkstraGraph represents a weighted and directed graph with non-negative edge
 * costs. The shortest paths in the graph are found using Dijkstra's algorithm.
 *
 * @author Spencer Hubbard
 */
// TODO: add method which returns true if graph is connected, false otherwise
// TODO: print some sort of visualization (possibly in driver program for a
// special graph)
public class DijkstraGraph {
  public static final float INFINITY = Float.MAX_VALUE;

  /**
   * Color used by Dijkstra's algorithm.
   *
   * @note
   * We distinguish between gray and black vertices to help understand the
   * algorithm.
   */
  public static enum Color {
    WHITE, GRAY, BLACK
  }

  // Map name of vertex to vertex object.
  private Map<String, Vertex> map;

  /**
   * Construct graph object.
   */
  public DijkstraGraph() {
    map = new HashMap<String, Vertex>();
  }

  /**
   * Adds a given weighted edge to this graph.
   *
   * @param tail
   * the name of the vertex at the tail of the given edge.
   *
   * @param head
   * the name of the vertex at the head of the given edge.
   *
   * @param cost
   * the weight of the given edge.
   *
   * @exception GraphException
   * if graph does not contain end-points of edge, edge is a multiple edge,
   * or cost is negative.
   */
  public void addEdge(String tail, String head, float cost) {
    // check preconditions
    if (containsEdge(tail, head)) {
      throw new GraphException("multiple edge");
    }
    if (cost < 0) {
      throw new GraphException("negative edge cost");
    }
    checkVertex(tail);
    checkVertex(head);
    // add edge to graph
    Vertex u = map.get(tail);
    Vertex v = map.get(head);
    Edge e = new Edge(v, cost);
    // add e to adjacency list for u
    u.adj.add(e);
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
    // check preconditions
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
      System.out.println();
    }
  }

  /**
   * Computes minimum cost path in this graph from a given source vertex to
   * all other vertexes in the connected component of this graph containing
   * the given source vertex.
   *
   * @note
   * Uses (binary min heap) priority queue to organize search.
   *
   * @note
   * Algorithm is similar to BFS.
   *
   * @param name
   * the name of the given source vertex.
   *
   * @exception GraphException
   * if this graph does not contain the given source vertex.
   */
  // TODO: document running time
  public void dijkstra1(String name) {
    // check preconditions
    checkVertex(name);
    // initialize bookkeeping fields
    reset();
    // use priority queue to organize search
    PriorityQueue<Path> heap = new PriorityQueue<Path>();
    // begin search at source vertex
    Vertex s = map.get(name);
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
            float cost = u.cost + e.cost;
            if (v.cost > cost) {
              v.cost = cost;
              v.last = u;
              heap.add(new Path(v, cost));
            }
          }
        }
        // u fully explored
        u.color = Color.BLACK;
      }
    }
    debug();
  }

  /**
   * Computes minimum cost path in this graph from a given source vertex to
   * all other vertexes in the connected component of this graph containing
   * the given source vertex.
   *
   * @note
   * Uses (pairing heap) priority queue with decrease key operation to
   * organize search.
   *
   * @note
   * Algorithm is similar to BFS.
   *
   * @param name
   * the name of the given source vertex.
   *
   * @exception GraphException
   * if this graph does not contain the given source vertex.
   */
  // TODO: document running time
  public void dijkstra2(String name) {
    // TODO: implement
    throw new RuntimeException("method not implemented");
  }

  /*
   * Debugging method that prints bookkeeping fields and path for each vertex
   * in graph after Dijkstra's algorithm.
   */
  private void debug() {
    System.out.print("debug output\n");
    System.out.printf("vertex: color: path:\n");
    for (Vertex v : map.values()) {
      System.out.printf("%-3.3s     %-5s  %-3.3s", v.name, v.color, v.name);
      Vertex u = v.last;
      while (u != null) {
        System.out.printf(" <-- %-3.3s", u.name);
        u = u.last;
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  // Reset bookkeeping fields to default values for each vertex in graph.
  private void reset() {
    for (Vertex v : map.values()) {
      v.reset();
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
    // Adjacency list for this vertex.
    public List<Edge> adj;
    // TODO: add field for position in pairing heap of path to this vertex
    // for dijkstra2

    // Bookkeeping field for total cost of path to this vertex.
    public float cost;
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
    public final float cost;

    // Construct edge with given head and cost.
    public Edge(Vertex head, float cost) {
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
    public float cost;

    // Construct path with given last vertex and cost.
    public Path(Vertex last, float cost) {
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
