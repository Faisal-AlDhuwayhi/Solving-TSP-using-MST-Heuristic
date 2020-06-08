import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

// Class represent MST (minimum spanning tree) the attributes of an instance of a MST.
public class MST {
	
	public Graph MSTGraph; // the graph of the minimum spanning tree.
	
	public MST() {
		MSTGraph = new Graph();
	}	
	
		// finding a minimum spanning tree from the given graph using Kruskal algorithm.
		public void kruskalMST(Graph graph) { // a metric graph is passing to the parameter 

			ArrayList<Vertex> vertices = graph.getVertices();
			for (int i = 0; i < vertices.size(); i++) {
				Vertex vertex = vertices.get(i);
				vertex.degree = 0;
				MSTGraph.addSpecificVertex(vertex);
			}
			
			// a priority queue for sorting the edges of the graph
			int numOfEdges = graph.getNumOfEdges();
			PriorityQueue<Edge> pq = new PriorityQueue<>(numOfEdges, Comparator.comparingInt(w -> w.weight));
			
			ArrayList<Edge> edges = graph.getGraph();
			// add all the edges to the priority queue,
			// sort the edges by weight.
			for (int i = 0; i < numOfEdges; i++) {
				pq.add(edges.get(i));
			}
			
			int numOfVertices = graph.getNumOfVertices();
			// a root array represent the root of each vertex.
			// which detect what tree every vertex on.
			int root[] = new int[numOfVertices];

			// makeSet
			makeSet(root,graph);

			int index = 0;
			while (index < numOfVertices - 1) {
				Edge edge = pq.remove();
				// check if adding this edge creates a cycle
				int srcSet = find(root, edge.source.id);
				int desSet = find(root, edge.destination.id);

				if (srcSet != desSet) {
					// add it to the MST graph
					edge.source.degree++;
					edge.destination.degree++;
					MSTGraph.addEdge(edge.source, edge.destination, edge.weight);
					index++;
					union(root, srcSet, desSet);
				}
			}
		}

		private void makeSet(int[] root,Graph graph) {
			// Make set- creating a new element with a parent pointer to itself.
			for (int i = 0; i < root.length; i++) {
				root[i] = i;
			}
			
		}
		
		private int find(int[] root, int vertex) {
			// chain of parent pointers from vertex upwards through the tree
			// until an element is reached whose root is itself
			if (root[vertex] != vertex)
				return find(root, root[vertex]);

			return vertex;
		}

		private void union(int[] root, int src, int des) {
			int srcSet = find(root, src);
			int desSet = find(root, des);
			// make src as or parent of des
			root[desSet] = srcSet;
		}

		
}
