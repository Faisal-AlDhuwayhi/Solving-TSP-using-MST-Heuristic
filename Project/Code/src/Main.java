
// Main class Used for running the program
public class Main {

	public static void main(String[] args) {

		// creating an instance of TSP graph
		Graph tspGraph = new Graph();

		// you can create an exact points for the vertices as shown below. make sure that you insert a
		// different data of points and ID for each one .
		// e.g. 738 -> x coordinate , 870 -> y coordinate , 0 -> ID
	
		/*
		 * Vertex v1 = new Vertex(738, 870, 0);
		 * Vertex v2 = new Vertex(745, 698, 1);
		 * Vertex v3 = new Vertex(41, 604, 2);
		 * Vertex v4 = new Vertex(92, 378, 3);
		 * Vertex v5 = new Vertex(679, 750, 4);
		 * Vertex v6 = new Vertex(180, 749, 5);
		 * tspGraph.addSpecificVertex(v1);
		 * tspGraph.addSpecificVertex(v2);
		 * tspGraph.addSpecificVertex(v3);
		 * tspGraph.addSpecificVertex(v4);
		 * tspGraph.addSpecificVertex(v5);
		 * tspGraph.addSpecificVertex(v6);
		 */

		// initializing the TSP graph

		// or you can create a random data for the vertices .
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();
		tspGraph.addVertex();

		
		tspGraph.generateMetricGraph();

		// print the information of the TSP graph
		System.out.println("Original Graph (TSP Graph):");
		tspGraph.printGraph();

		System.out.println("--------------------------------");

		// print the information of the optimal solution
		System.out.println("Optimal solution Graph:");

		// create an instance of the Optimal solution of TSP
		OptimalTSP optimalTSP = new OptimalTSP();
		int min_pathWeigth = optimalTSP.TSPsolution(tspGraph, 0);
		System.out.println("Optimal Cost traverse = " + min_pathWeigth);
		optimalTSP.printOptPath();
		optimalTSP.TSPOptGraph.printGraph();

		System.out.println("--------------------------------");

		// create an instance of approximation solution of TSP
		ApproxAlg approxAlg = new ApproxAlg();
		int approxWeight = approxAlg.christofid(tspGraph);

		// print the information of the approximation solution
		System.out.println("Approximation solution Graph:");

		System.out.println("Approximation Cost traverse = " + approxWeight);

		approxAlg.printApproxPath();
		approxAlg.chritoGraph.printGraph();

		System.out.println("--------------------------------");

		// print the Comparison between the optimal and approximation solution
		System.out.println("Comparison between the optimal and approximation solution : ");
		double ratio = (approxWeight * 1.0 / min_pathWeigth);
		System.out.println(ratio); // at most 1.5-opt

	}

}
