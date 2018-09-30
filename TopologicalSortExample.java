/**
 * This program contains an example for topological sort algorithm.
 *
 * @author Spencer Hubbard
 *
 */
public class TopologicalSortExample {
  public static void main(String[] args) {
    TopologicalSortGraph g = new TopologicalSortGraph();
    g.addVertex("a");
    g.addVertex("b");
    g.addVertex("c");
    g.addVertex("d");
    g.addVertex("e");
    g.addVertex("f");
    g.addVertex("g");
    g.addEdge("a", "d");
    g.addEdge("a", "e");
    g.addEdge("a", "g");
    g.addEdge("b", "c");
    g.addEdge("b", "e");
    g.addEdge("b", "f");
    g.addEdge("c", "d");
    g.addEdge("c", "e");
    g.addEdge("d", "e");
    g.addEdge("e", "f");
    g.addEdge("e", "g");
    g.addEdge("f", "g");

    System.out.println("Graph:");
    g.printAdjacencyList();
    System.out.println();

    System.out.println("Topological sort:");
    g.topologicalSort();
  }
}
