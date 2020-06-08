import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;

// Class Vertex represent the attributes of an instance of a Vertex.
class Vertex {
	int id; // ID that distinguishes the vertex 
	int x; // represent the x coordinate of the vertex.
	int y; // represent the y coordinate of the vertex.
	int degree; // represent the number of associated vertex with this vertex.
	
	public Vertex(int x, int y, int id) { 
		this.id = id;
		this.x = x;
		this.y = y;
		this.degree = 0;
	}
	public Vertex(Vertex copyVertex) { // copy constructor
		this.id = copyVertex.id;
		this.x = copyVertex.x;
		this.y = copyVertex.y;
		this.degree = copyVertex.degree;
	}
	
}

// Class Edge represent the attributes of an instance of an Edge.
class Edge {
	Vertex source; 
	Vertex destination;
	int weight;

	public Edge(Vertex source, Vertex destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;

	}
	public Edge(Edge copyEdge) {  // copy constructor
		Vertex copyVertexSrc = new Vertex(copyEdge.source);
		Vertex copyVertexDes = new Vertex(copyEdge.destination);
		this.source = copyVertexSrc;
		this.destination = copyVertexDes;
		this.weight = copyEdge.weight;

	}
}

// Class Graph represent the attributes of an instance of a Graph.
public class Graph {
	private ArrayList<Edge> edges; // represent the edges of the graph
	private ArrayList<Vertex> vertices; // represent the edges of the graph
	private int id; // to be used when adding a vertex in the graph.

	public Graph() { 
		edges = new ArrayList<>();
		vertices = new ArrayList<Vertex>();
		id = 0;
	}
	
	// return the edges of the graph 
	public ArrayList<Edge> getGraph() {
		return edges;
	}

	// return the vertices of the graph 
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	// return the number of edges in the graph 
	public int getNumOfEdges() {
		return edges.size();
	}
	
	// return the number of vertices in the graph 
	public int getNumOfVertices() {
		return vertices.size();
	}

	// add a vertex of random coordinates
	public void addVertex() {
		Random random = new Random();
		int x = random.nextInt(1000), y = random.nextInt(1000);
		Vertex vertex = new Vertex(x, y, id++);
		while (true) {
			if (!isVertex(vertex))
				break;
			x = random.nextInt(1000);
			y = random.nextInt(1000);
			vertex.x = x;
			vertex.y = y;

		}
		vertices.add(vertex);
	}

	// add a vertex from a given data
	public void addSpecificVertex(Vertex vertex) {
		if(!isVertex(vertex)) {
			vertices.add(vertex);
		}
	}
	
	// check if there is the same of vertex in the graph of the given vertex.
	// if yes return true, otherwise return false
	public boolean isVertex(Vertex checkVertex) {
		for (int i = 0; i < vertices.size(); i++) {
			Vertex vertex = vertices.get(i);
			if (vertex.id == checkVertex.id || vertex.x == checkVertex.x && vertex.y == checkVertex.y) 
				return true;
		}
		return false;
	}
	// check if there is the same of vertex in the graph of the given vertex by its' ID.
	// if yes return the vertex, otherwise return null
	public Vertex isVertexObjectById(int checkVertex) {
		for (int i = 0; i < vertices.size(); i++) {
			Vertex vertex = vertices.get(i);
			if (vertex.id == checkVertex) 
				return vertex;
		}
		return null;
	}
	
	// add edge in the graph. return true if the edge is added, otherwise false.
	public boolean addEdge(Vertex source, Vertex destination, int weight) {
		if (source == destination)
			return false;
		
		Edge edge = new Edge(source, destination, weight);
		edges.add(edge);
		
		return true;
	}

	// check if there is the same of edge in the graph of the given edge.
	// if yes return true, otherwise return false
	public boolean isEdge(Vertex source, Vertex destination) {
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			if (edge.source.id == source.id && edge.destination.id == destination.id 
					|| edge.source.id == destination.id && edge.destination.id == source.id)
				return true;
		}
		return false;
	}
	
	// check if there is the same of edge in the graph of the given edge by its' source and destination.
	// if yes return the edge, otherwise return null
	public Edge isEdgeObjectById(int source, int destination) {
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			if (edge.source.id == source && edge.destination.id == destination)
				return edge;
			else if (edge.source.id == destination && edge.destination.id == source) { // because the graph is undirected
				Edge newEdge = new Edge(edge.destination,edge.source,edge.weight);
				return newEdge;
			}
		}
		
		return null;
	}

	// remove a specific edge in the graph by its' source and destination.
	public void removeEdge(Vertex source, Vertex destination) {
		if (isEdge(source, destination)) {
			for (int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);
				if (edge.source.id == source.id && edge.destination.id == destination.id 
						|| edge.source.id == destination.id && edge.destination.id == source.id) {
					edges.remove(i);
					break;
				}
			}
		}
	}
	
	
	// make the graph a metric graph.
	public void generateMetricGraph() {
		// link each vertex with all the other vertices
		for (int i = 0; i < vertices.size(); i++) {
			Vertex src = vertices.get(i);
			for (int j = i + 1; j < vertices.size(); j++) {
				Vertex des = vertices.get(j);
				int weight = (int) Math.sqrt(Math.pow(src.x - des.x, 2) + Math.pow(src.y - des.y, 2));
				addEdge(src, des, weight);
			}

		}

	}
	
	// return the graph information as an adjacency matrix .
	@SuppressWarnings("unchecked")
	public ArrayList<Integer>[] toAdjacencyMatrix() {
		int numOfVertices = vertices.size();
		ArrayList<Integer> AdjMat[] = new ArrayList[numOfVertices]; 
		for (int i = 0; i < numOfVertices; i++) { 
			AdjMat[i] = new ArrayList<Integer>(); 
        } 
		
		for (int i = 0; i < numOfVertices; i++) {
			for (int j = 0; j < numOfVertices; j++) {
				AdjMat[i].add(j, 0);
			}
		}
		
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			int source = edge.source.id, destination = edge.destination.id;
			AdjMat[source].set(destination, edge.weight);
			AdjMat[destination].set(source, edge.weight);
		}

		return AdjMat;
	}
	
	
	// return the graph information as an adjacency list .
	@SuppressWarnings("unchecked")
	public LinkedList<Integer>[] toAdjacencyList() {
		int numOfVertices = vertices.size();
		LinkedList<Integer> Adjlist[] = new LinkedList[numOfVertices]; 
		for (int i = 0; i < numOfVertices; i++) { 
			Adjlist[i] = new LinkedList<Integer>(); 
        } 
		
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			int source = edge.source.id, destination = edge.destination.id;
			Adjlist[source].add(destination);
			Adjlist[destination].add(source);
		}

		return Adjlist;
	}
	
	// print the graph information.
	public void printGraph() {
		System.out.print("the Cities: ");
		for (int i = 0; i < vertices.size(); i++) {
			Vertex vertex = vertices.get(i);
			if(i != vertices.size()-1)
				System.out.print(vertex.id + ", ");
			else 
				System.out.println(vertex.id);
		}
	
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			System.out.println(
					"Edge " + i + " -> city: " + edge.source.id + ", city: " + edge.destination.id + ", weight: " + edge.weight);
		}
	}
	
}
