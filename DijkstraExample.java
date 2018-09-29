/**
 * This program contains an example for Dijkstra's algorithm.
 *
 * @author Spencer Hubbard
 *
 */
public class DijkstraExample {
  public static void main(String[] args) {
    DijkstraGraph g = new DijkstraGraph();
    g.addVertex("a"); // a
    g.addVertex("b"); // b
    g.addVertex("c"); // c
    g.addVertex("d"); // d
    g.addVertex("e"); // e
    g.addVertex("f"); // f
    g.addVertex("g"); // g
    g.addEdge("a", "b", 2); // (a, b, 2)
    g.addEdge("a", "d", 1); // (a, d, 1)
    g.addEdge("b", "d", 3); // (b, d, 3)
    g.addEdge("b", "e", 10); // (b, e, 10)
    g.addEdge("c", "a", 4); // (c, a, 4)
    g.addEdge("c", "f", 5); // (c, f, 5)
    g.addEdge("d", "c", 2); // (d, c, 2)
    g.addEdge("d", "e", 2); // (d, e, 2)
    g.addEdge("d", "f", 8); // (d, f, 8)
    g.addEdge("d", "g", 4); // (d, g, 4)
    g.addEdge("e", "g", 6); // (e, g, 6)
    g.addEdge("g", "f", 1); // (g, f, 1)

    System.out.println("Graph:");
    g.printAdjacencyMatrix();
    System.out.println();

    System.out.println("Shortest paths from vertex a:");
    g.dijkstra1("a");
  }
}
