/**
 * This program contains an example for Prim's algorithm.
 * 
 * @author Spencer Hubbard
 *
 */
public class PrimExample {
	public static void main(String[] args) {
		PrimGraph g = new PrimGraph();
		g.addVertex("a"); // a or 1
		g.addVertex("b"); // b or 2
		g.addVertex("c"); // c or 3
		g.addVertex("d"); // d or 4
		g.addVertex("e"); // e or 5
		g.addVertex("f"); // f or 6
		g.addVertex("g"); // g or 7
		g.addEdge("a", "b", 2); // (a, b, 2) or (1, 2, 2)
		g.addEdge("a", "c", 4); // (a, c, 1) or (1, 3, 4)
		g.addEdge("a", "d", 1); // (a, d, 1) or (1, 4, 1)
		g.addEdge("b", "d", 3); // (b, d, 3) or (2, 4, 3)
		g.addEdge("b", "e", 10); // (b, e, 10) or (2, 5, 10)
		g.addEdge("c", "d", 2); // (c, d, 2) or (3, 4, 2)
		g.addEdge("c", "f", 5); // (c, f, 5) or (3, 6, 5)
		g.addEdge("d", "e", 7); // (d, e, 7) or (4, 5, 7)
		g.addEdge("d", "f", 8); // (d, f, 8) or (4, 6, 8)
		g.addEdge("d", "g", 4); // (d, g, 4) or (4, 7, 4)
		g.addEdge("e", "g", 6); // (e, g, 6) or (5, 7, 6)
		g.addEdge("f", "g", 1); // (f, g, 1) or (6, 7, 1)

		System.out.println("Graph adjacency matrix:");
		g.printAdjacencyMatrix();
		System.out.println();

		System.out.println("Minimum spanning tree:");
		g.prim1();
	}
}
