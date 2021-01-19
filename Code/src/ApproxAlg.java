import java.util.ArrayList;

// Class ApproxAlg represent the attributes of an instance of an approximation algorithm.
public class ApproxAlg {
	
	// the resulting graph from implementing the Christofid algorithm
	public Graph chritoGraph;

	public ApproxAlg() {
		chritoGraph = new Graph();
	}

	// let's call the graph in the parameter as G
	public int christofid(Graph graph) {

		// make a MST
		MST mst = new MST();
		// 1) Calculating a minimum spanning tree by Kruskal algorithm, let's call the resulting 
		// MST as T
		mst.kruskalMST(graph);
		//mst.MSTGraph.printGraph();

		// 2) Construct a minimum-weight perfect matching (Let's call it M) from the odd vertices of T. a perfect
		// matching is where every vertex is connected by one edge.
		Graph minWPMGrap = minWeigthPerfectMatch(mst.MSTGraph);
		//minWPMGrap.printGraph();

		// 3) make the combined graph, that combine the graphs M (minimum-weight perfect
		// matching) and T (minimum spanning tree),
		// to make every vertex has an even degree so we can make an Eulerian Circuit.
		Graph combinedGraph = combineGraph(mst.MSTGraph, minWPMGrap);
		//combinedGraph.printGraph();

		// 4) make an Eulerian Circuit (it's called also Euler tour). an Eulerian Circuit
		// is a cycle that visits every edge in the graph once.
		EulerianCircuit EuCrcuit = new EulerianCircuit();
		EuCrcuit.FleuryAlgorithm(combinedGraph);
		//EuCrcuit.euGraph.printGraph();

		// 5) make shortcuts on the Eulerian Circuit (Removing repeated vertices) , and done.
		shortcutGraph(EuCrcuit.euGraph);
		
		
		// calculating the weight of the approximation solution
		ArrayList<Edge> edges = chritoGraph.getGraph();
		int approxWeight = 0;
		for (int i = 0; i < edges.size(); i++) {
			approxWeight += edges.get(i).weight;
		}
		
		return approxWeight;

	}

	
	public void shortcutGraph(Graph EuCrcuit) {
		// filling the vertices of the Christofid graph
		ArrayList<Vertex> vertices = EuCrcuit.getVertices();
		for (int i = 0; i < vertices.size(); i++) {
			Vertex vertex = vertices.get(i);
			chritoGraph.addSpecificVertex(vertex);
		}
		
		// isVisited array is to check if we visit the vertex before or not.
		boolean isVisited[] = new boolean[vertices.size()];
		
		ArrayList<Edge> edges = EuCrcuit.getGraph();
		int i = 0;
		isVisited[0] = true;
		while (i < edges.size()) {
			int id = edges.get(i).destination.id;
			if (!isVisited[id] || i == edges.size() - 1) { // not visited yet
				Edge edge = edges.get(i++);
				chritoGraph.addEdge(edge.source, edge.destination, edge.weight);
				isVisited[id] = true;
			} else {
				Edge edgeBefore = edges.get(i++);
				Edge edgeAfter = edges.get(i++);
				Vertex vertexsrc = edgeBefore.source;
				Vertex vertexdes = edgeAfter.destination;
				int weigth = (int) Math
						.sqrt(Math.pow(vertexsrc.x - vertexdes.x, 2) + Math.pow(vertexsrc.y - vertexdes.y, 2));
				chritoGraph.addEdge(vertexsrc, vertexdes, weigth);
				int des = edgeAfter.destination.id;
				isVisited[des] = true;
			}

		}
	}

	public Graph combineGraph(Graph mstGraph, Graph mWPMGrap) {
		Graph combineGraph = new Graph();
		ArrayList<Vertex> vertices = mstGraph.getVertices();
		for (int i = 0; i < vertices.size(); i++) {
			Vertex vertex = vertices.get(i);
			combineGraph.addSpecificVertex(vertex);
		}
		ArrayList<Edge> edgesMst = mstGraph.getGraph();
		for (int i = 0; i < edgesMst.size(); i++) {
			Edge edge = edgesMst.get(i);
			combineGraph.addEdge(edge.source, edge.destination, edge.weight);
		}

		ArrayList<Edge> edgesmWPM = mWPMGrap.getGraph();
		for (int i = 0; i < edgesmWPM.size(); i++) {
			Edge edge = edgesmWPM.get(i);
			combineGraph.addEdge(edge.source, edge.destination, edge.weight);
		}

		return combineGraph;
	}

	public Graph minWeigthPerfectMatch(Graph mstGraph) {
		Graph mWPMGraph = new Graph();
		ArrayList<Vertex> oddvertices = findOddNodes(mstGraph);
		for (int i = 0; i < oddvertices.size(); i++) {
			mWPMGraph.addSpecificVertex(oddvertices.get(i));
		}
		int i = 0;
		while (i < oddvertices.size()) {
			Vertex vertex1 = oddvertices.get(i++);
			Vertex vertex2 = oddvertices.get(i++);
			vertex1.degree++;
			vertex2.degree++;
			int weight = (int) Math.sqrt(Math.pow(vertex1.x - vertex2.x, 2) + Math.pow(vertex1.y - vertex2.y, 2));
			mWPMGraph.addEdge(vertex1, vertex2, weight);
		}
		return mWPMGraph;
	}

	// find the vertices that have odd degree in the given graph
	private ArrayList<Vertex> findOddNodes(Graph graph) {
		ArrayList<Vertex> oddVertices = new ArrayList<Vertex>();
		ArrayList<Vertex> mstVertices = graph.getVertices();

		for (int i = 0; i < mstVertices.size(); i++) {
			Vertex vertex = mstVertices.get(i);
			if (vertex.degree % 2 != 0) {
				oddVertices.add(vertex);
			}
		}

		return oddVertices;
	}

	// print the approximation path 
	public void printApproxPath() {
		ArrayList<Edge> approxEdges = chritoGraph.getGraph();
		System.out.print("Approximation cost path = [");
		for (int i = 0; i < approxEdges.size(); i++) {
			Vertex vertex = approxEdges.get(i).source;
			System.out.print(" " + vertex.id + ",");
		}
		Vertex vertex = approxEdges.get(0).source;
		System.out.print(" " + vertex.id + " ");
		System.out.println("]");

	}

}
