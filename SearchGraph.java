import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * SearchGraph represents a undirected graph with no multiple edge and is used
 * for the BFS and DFS algorithms.
 *
 * @author Spencer Hubbard
 *
 */
// TODO: document graph algorithms
// TODO: add method which returns true if graph is connected, false otherwise
// TODO: add method which returns true if graph is bipartite, false otherwise
// TODO: print some sort of visualization (possibly in driver program for a
// special graph)
public class SearchGraph {
  /**
   * Color used by BFS and DFS.
   *
   * @note
   * We distinguish between gray and black vertices to help understand the
   * algorithms.
   */
  public static enum Color {
    WHITE, GRAY, BLACK
  }

  // Map name of vertex to vertex object.
  private Map<String, Vertex> map;

  /**
   * Construct graph object.
   */
  public SearchGraph() {
    map = new HashMap<String, Vertex>();
  }

  /**
   * Adds a given edge to this graph.
   *
   * @param name1
   * the name of the vertex at one endpoint of the given edge.
   *
   * @param name2
   * the name of the vertex at other endpoint of the given edge.
   *
   * @exception GraphException
   * if graph does not contain end-points of edge or edge is a multiple edge.
   */
  public void addEdge(String name1, String name2) {
    checkVertex(name1);
    checkVertex(name2);
    if (containsEdge(name1, name2)) {
      throw new GraphException("multiple edge");
    }
    // TODO: check if edge is loop
    Vertex u = map.get(name1);
    Vertex v = map.get(name2);
    u.adj.add(v);
    v.adj.add(u);
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
   * Does breadth-first search of this graph starting with a given source
   * vertex.
   *
   * @note
   * Use queue to organize search.
   *
   * @param name
   * the name of the given source vertex.
   *
   * @exception GraphException
   * if this graph does not contain the given source vertex.
   */
  // TODO: document running time
  public void breadthFirstSearch(String name) {
    checkVertex(name);
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
   *
   * @note
   * Use stack to organize search.
   *
   * @param name
   * the name of the given source vertex.
   *
   * @exception GraphException
   * if this graph does not contain the given source vertex.
   */
  // TODO: document running time
  public void depthFirstSearchI(String name) {
    checkVertex(name);
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
   *
   * @note
   * Use recursion to organize search.
   *
   * @param name
   * the name of the given source vertex.
   *
   * @exception GraphException
   * if this graph does not contain the given source vertex.
   */
  // TODO: document running time
  public void depthFirstSearchR(String name) {
    checkVertex(name);
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
    System.out.print("debug output\n");
    System.out.printf("vertex: color: distance: path:\n");
    for (Vertex v : map.values()) {
      System.out.printf("%-3.3s     %-5s  %-3d       %-3.3s", v.name, v.color, v.dist, v.name);
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
    System.out.print("debug output\n");
    System.out.printf("vertex: color: left: right: distance: path:\n");
    for (Vertex v : map.values()) {
      System.out.printf("%-3.3s     %-5s  %-3d   %-3d    %-3d       %-3.3s", v.name, v.color, v.left, v.right,
          v.dist, v.name);
      Vertex u = v.path;
      while (u != null) {
        System.out.printf(" <-- %-3.3s", u.name);
        u = u.path;
      }
      System.out.print("\n");
    }
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
    public List<Vertex> adj;

    // Bookkeeping field for recursive DFS.
    public int left;
    // Bookkeeping field for recursive DFS.
    public int right;
    // Bookkeeping field for length of path to this vertex.
    public int dist;
    // Bookkeeping field for previous vertex in path to this vertex.
    public Vertex path;
    // Bookkeeping field for color of this vertex.
    public Color color;

    // Construct vertex with given name.
    public Vertex(String name) {
      this.name = name;
      adj = new LinkedList<Vertex>();
      reset();
    }

    // Set bookkeeping fields to default values for this vertex.
    public void reset() {
      left = 0;
      right = 0;
      dist = 0;
      path = null;
      color = Color.WHITE;
    }
  }
}
