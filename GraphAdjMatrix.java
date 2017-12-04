import java.util.ArrayList;
import java.util.Stack;

public class GraphAdjMatrix implements Graph {
	private int[][] matrix;
	private int vertices;

	public GraphAdjMatrix() {
		matrix = new int[0][0];
		vertices = 0;
	}

	public GraphAdjMatrix(int v) {
		matrix = new int[v][v];
		vertices = v;
	}

	public void addEdge(int v1, int v2) {
		matrix[v1][v2] = 1;
		matrix[v2][v1] = 1;
	}

	public void topologicalSort() {
		if (vertices == 0) {
			System.out.println("No vertex in the graph!");
			return;
		}
		Stack<Integer> s = new Stack<Integer>();
		int[] indegree = new int[vertices];
		int count = 0;
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				if (matrix[j][i] > 0) {
					if (i == j) {
						System.out.println("There is a circle.");
						return;
					}
					indegree[i] += 1;
				}
			}
			if (indegree[i] == 0)
				s.push(i);
		}
		while (!s.isEmpty()) {
			int v = s.pop(); // the next vertex
			System.out.print(v + " ");
			count += 1;
			for (int i = 0; i < vertices; i++) {
				if (matrix[v][i] > 0 && --indegree[i] == 0)
					s.push(i);
			}
		}
		System.out.println();
		if (count != vertices)
			System.out.println("There is a circle.");
	}

	public void addEdge(int v1, int v2, int weight) {
		matrix[v1][v2] = weight;
		matrix[v2][v1] = weight;
	}

	public int getEdge(int v1, int v2) {
		return matrix[v1][v2];
	}

	public int createSpanningTree() {
		GraphAdjMatrix newGraph = new GraphAdjMatrix(vertices);
		ArrayList<Integer> known = new ArrayList<Integer>();
		ArrayList<Integer> unknown = new ArrayList<Integer>();
		int total = 0;
		for (int i = 1; i < vertices; i++) {
			unknown.add(i);
		}
		known.add(0);
		for (int i = 0; i < vertices - 1; i++) {
			// find the minimum weight edge from known to unknown
			Integer from = -1, to = -1;
			int weight = Integer.MAX_VALUE;
			for (int fromIndex : known) {
				for (int toIndex : unknown) {
					int w = matrix[fromIndex][toIndex];
					if (w > 0 && w < weight) {
						from = fromIndex;
						to = toIndex;
						weight = matrix[fromIndex][toIndex];
					}
				}
			}
			known.add(to);
			unknown.remove(to);
			newGraph.addEdge(from, to, weight);
			total += weight;
		}
		matrix = newGraph.matrix;
		return total;
	}

}
