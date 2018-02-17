/**
 * This program contains examples for BFS and DFS algorithms.
 * 
 * @author Spencer Hubbard
 *
 */
public class SearchExample {
	public static void main(String[] args) {
		// simple example
		SearchGraph g = new SearchGraph();
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		g.addVertex("f");
		g.addVertex("g");
		g.addVertex("h");
		g.addEdge("a", "b");
		g.addEdge("a", "c");
		g.addEdge("b", "c");
		g.addEdge("b", "d");
		g.addEdge("b", "e");
		g.addEdge("c", "e");
		g.addEdge("c", "g");
		g.addEdge("c", "h");
		g.addEdge("d", "e");
		g.addEdge("e", "f");
		g.addEdge("g", "h");

		System.out.println("Graph:");
		g.printAdjacencyList();
		System.out.println();

		System.out.println("BFS from vertex a:");
		g.breadthFirstSearch("a");
		System.out.println();

		System.out.println("Iterative DFS from vertex a:");
		g.depthFirstSearchI("a");
		System.out.println();

		System.out.println("Recursive DFS from vertex a:");
		g.depthFirstSearchR("a");
	}
}
