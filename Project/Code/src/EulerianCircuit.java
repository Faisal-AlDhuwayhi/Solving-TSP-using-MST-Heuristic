import java.util.ArrayList;
import java.util.LinkedList;

// Class Eulerian Circuit represent the attributes of an instance of an Eulerian Circuit.
public class EulerianCircuit {

	public Graph euGraph; // the resulting graph of the Eulerian Circuit.
	public LinkedList<Integer>[] adjlist; // to be used in determining the Eulerian Circuit.

	public EulerianCircuit() {
		euGraph = new Graph();
	}

	// find the Eulerian Circuit of the given graph by implementing Fleury Algorithm.
	public void FleuryAlgorithm(Graph combineGraph) {
		// adding vertices
		ArrayList<Vertex> combineVertices = combineGraph.getVertices();
		for (int i = 0; i < combineVertices.size(); i++) {
			Vertex vertex = combineVertices.get(i);
			euGraph.addSpecificVertex(vertex);
		}
		// convert the graph edges to Adjacency list to start processing the algorithm
		adjlist = combineGraph.toAdjacencyList();

		// Find a vertex with odd degree if exist to start with
		int src = 0;
		ArrayList<Vertex> euVertices = euGraph.getVertices();
		for (int i = 0; i < euVertices.size(); i++) {
			Vertex vertex = euVertices.get(i);
			if (vertex.degree % 2 == 1) {
				src = i;
				break;
			}
		}

		// the method that gives us the Euler tour
		findEulerTour(src, combineGraph);
	}

	// find an Euler tour starting from vertex src
	private void findEulerTour(Integer src, Graph combineGraph) {
		// repeat for all the vertices adjacent to src vertex
		for (int i = 0; i < adjlist[src].size(); i++) {
			Integer des = adjlist[src].get(i);
			// check if edge src-des is a valid next edge
			if (isValidNextEdge(src, des)) {
				// add it to the edges of Eulerian graph (euGraph)
				Edge edge = combineGraph.isEdgeObjectById(src, des);
				euGraph.addEdge(edge.source, edge.destination, edge.weight);

				// This edge is used, so we remove it from the adjacency list
				adjlist[src].remove(des);
				adjlist[des].remove(src);
				// go to do the same for srcs' adjacents.
				findEulerTour(des, combineGraph);
			}
		}
	}

	// this method is to check if edge src-des can be
	// considered as next edge in the Euler Tour or not
	private boolean isValidNextEdge(Integer src, Integer des) {
		// The edge src-des is valid in one of the
		// following two cases:

		// 1) If des is the only adjacent vertex of src
		if (adjlist[src].size() == 1)
			return true;

		// 2) If src has multiple adjacents, then
		// we have to check if the edge src-des is a bridge or not. to do that, we have
		// to do following steps:
		// 2.1) Counting vertices that are reachable from src
		boolean isVisited[] = new boolean[euGraph.getNumOfVertices()];
		int countWith = DFSCount(src, isVisited);

		// 2.2) Remove edge src-des
		adjlist[src].remove(des);
		adjlist[des].remove(src);

		// 2.3) Counting vertices that are reachable from src after removing the edge
		// src-des
		isVisited = new boolean[euGraph.getNumOfVertices()];
		int countWithout = DFSCount(src, isVisited);

		// 2.4) Add the edge back to the graph
		adjlist[src].add(des);
		adjlist[des].add(src);

		// If number of reachable vertices are reduced after removing the edge src-des,
		// then edge src-des is a bridge
		return (countWith > countWithout) ? false : true;
	}

	// A DFS based function to count reachable vertices from src vertex
	private int DFSCount(Integer src, boolean isVisited[]) {
		// Mark the current node as visited
		isVisited[src] = true;
		int count = 1;
		// repeat for all vertices adjacent to this vertex
		for (int i = 0; i < adjlist[src].size(); i++) {
			int vertex = adjlist[src].get(i);
			if (!isVisited[vertex]) {
				count += DFSCount(vertex, isVisited);
			}
		}
		return count;
	}

}
