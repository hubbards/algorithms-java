/**
 * This program contains an example for topological sort algorithm.
 * 
 * @author Spencer Hubbard
 *
 */
public class TopologicalSortExample {
	public static void main(String[] args) {
		// simple example
		TopologicalSortGraph g = new TopologicalSortGraph();
		g.addVertex("a"); // a or 1
		g.addVertex("b"); // b or 2
		g.addVertex("c"); // c or 3
		g.addVertex("d"); // d or 4
		g.addVertex("e"); // e or 5
		g.addVertex("f"); // f or 6
		g.addVertex("g"); // g or 7
		g.addEdge("a", "d"); // (a, d) or (1, 4)
		g.addEdge("a", "e"); // (a, e) or (1, 5)
		g.addEdge("a", "g"); // (a, g) or (1, 7)
		g.addEdge("b", "c"); // (b, c) or (2, 3)
		g.addEdge("b", "e"); // (b, e) or (2, 5)
		g.addEdge("b", "f"); // (b, f) or (2, 6)
		g.addEdge("c", "d"); // (c, d) or (3, 4)
		g.addEdge("c", "e"); // (c, e) or (3, 5)
		g.addEdge("d", "e"); // (d, e) or (4, 5)
		g.addEdge("e", "f"); // (e, f) or (5, 6)
		g.addEdge("e", "g"); // (e, g) or (5, 7)
		g.addEdge("f", "g"); // (f, g) or (6, 7)

		System.out.println("Graph:");
		g.printAdjacencyList();
		System.out.println();

		System.out.println("Topological sort:");
		g.topologicalSort();
	}
}
