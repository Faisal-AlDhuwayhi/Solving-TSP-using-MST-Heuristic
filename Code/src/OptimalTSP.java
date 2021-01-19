import java.util.ArrayList;

public class OptimalTSP {

	public Graph TSPOptGraph; // TSP Optimal graph Solution
	private ArrayList<Vertex> MinPath; // the minimum path (optimal) of the TSP solution.

	public OptimalTSP() {
		TSPOptGraph = new Graph();
		MinPath = new ArrayList<Vertex>();
	}

	// Find the optimal solution of the TSP problem
	public int TSPsolution(Graph originalGraph, int start) {
		// the array list that save the permutation in each permutation we try.
		ArrayList<Integer> PerVertices = new ArrayList<Integer>();
		// the array list that save the
		ArrayList<Vertex> current_path = new ArrayList<Vertex>();

		ArrayList<Vertex> vertices = originalGraph.getVertices();
		for (int i = 0; i < vertices.size(); i++) {
			Vertex vertex = vertices.get(i);
			TSPOptGraph.addSpecificVertex(vertex);
		}

		ArrayList<Integer>[] adjMatrix = originalGraph.toAdjacencyMatrix();

		// we add all the vertices in the vertex list except the start vertex
		for (int i = 0; i < adjMatrix.length; i++)
			if (i != start)
				PerVertices.add(i);

		int min_pathWeigth = Integer.MAX_VALUE;
		do {

			int current_pathweight = 0;
			// delete the path so may find a better one
			current_path.removeAll(current_path);

			// compute current path weight
			int currentVertexId = start;
			// calculate the path weight
			for (int i = 0; i < PerVertices.size(); i++) {
				current_pathweight += adjMatrix[currentVertexId].get(PerVertices.get(i));
				currentVertexId = PerVertices.get(i);
				Vertex currentVertex = TSPOptGraph.isVertexObjectById(currentVertexId);
				current_path.add(currentVertex);
			}
			current_pathweight += adjMatrix[currentVertexId].get(start);

			// get the minimum path of the current_pathweight and the min_pathWeigth
			min_pathWeigth = min(min_pathWeigth, current_pathweight, current_path);

		} while (findNextPermutation(PerVertices)); // find the next path until the vertex array get reversed

		// adding the start Vertex to the solution path
		Vertex startVertex = TSPOptGraph.isVertexObjectById(start);
		MinPath.add(0, startVertex);
		MinPath.add(startVertex);

		// adding the final path to edges list of the OptimalTSP's graph
		for (int i = 0; i < MinPath.size() - 1; i++) {
			Vertex src = MinPath.get(i);
			Vertex des = MinPath.get(i + 1);
			int weigth = adjMatrix[src.id].get(des.id);
			TSPOptGraph.addEdge(src, des, weigth);
		}

		return min_pathWeigth;
	}

	@SuppressWarnings("unchecked")
	// find the minimum between the min_pathWeight and the current_pathweight,
	// and if current_pathweight < min_pathWeight, change the minimum path to the
	// current path.
	private int min(int min_pathWeight, int current_pathWeight, ArrayList<Vertex> current_path) {
		if (min_pathWeight > current_pathWeight) {
			MinPath.removeAll(MinPath);
			MinPath = (ArrayList<Vertex>) current_path.clone();
			return current_pathWeight;
		}
		return min_pathWeight;
	}

	// find the next permutation (path) until the PerVertices list get reversed
	// i.e. [ 1,2,3,4 ] ........ [ 4,3,2,1 ]
	private boolean findNextPermutation(ArrayList<Integer> data) {
		// If the given data-set is empty
		// or contains only one element
		// next permutation is not possible
		if (data.size() <= 1)
			return false;

		int last = data.size() - 2;

		// find the longest non-increasing suffix
		// and find the pivot
		while (last >= 0) {
			if (data.get(last) < data.get(last + 1)) {
				break;
			}
			last--;
		}

		// If there is no increasing pair
		// there is no higher order permutation
		if (last < 0)
			return false;

		int nextGreater = data.size() - 1;

		// Find the rightmost successor to the pivot
		for (int i = data.size() - 1; i > last; i--) {
			if (data.get(i) > data.get(last)) {
				nextGreater = i;
				break;
			}
		}

		// Swap the successor and the pivot
		data = swap(data, nextGreater, last);

		// Reverse the suffix
		data = reverse(data, last + 1, data.size() - 1);

		// Return true as the next permutation is done
		return true;
	}

	// swap between left index and right index
	private ArrayList<Integer> swap(ArrayList<Integer> data, int left, int right) {

		// Swap the data
		int temp = data.get(left);
		data.set(left, data.get(right));
		data.set(right, temp);

		// Return the updated array
		return data;
	}

	// Function to reverse the sub-array, starting from left to the right, both
	// inclusive .
	private ArrayList<Integer> reverse(ArrayList<Integer> data, int left, int right) {

		// Reverse the sub-array
		while (left < right) {
			int temp = data.get(left);
			data.set(left, data.get(right));
			left++;
			data.set(right, temp);
			right--;
		}

		// Return the updated array
		return data;
	}

	// print the optimal path
	public void printOptPath() {

		System.out.print("Optimal cost path = [");
		for (int i = 0; i < MinPath.size(); i++) {
			Vertex vertex = MinPath.get(i);
			if (i == MinPath.size() - 1)
				System.out.print(" " + vertex.id + " ");
			else
				System.out.print(" " + vertex.id + ",");
		}
		System.out.println("]");

	}

}
