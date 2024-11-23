package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a graph structure consisting of vertices and edges.
 * The graph supports basic operations such as adding, removing, and querying vertices and edges.
 */
public class Graph {
    private List<Vertex> vertices; /// List of vertices in the graph.
    private Set<Edge> edges; /// Set of edges in the graph.

    /**
     * Default constructor that initializes an empty graph.
     */
    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new HashSet<>();
    }

    /**
     * Constructs a graph with a given list of vertices and set of edges.
     * @param vertices Initial list of vertices.
     * @param edges Initial set of edges.
     */
    public Graph(List<Vertex> vertices, Set<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    /**
     * Retrieves the list of vertices in the graph.
     * @return List of vertices.
     */
    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Sets the list of vertices in the graph.
     * @param vertices The list of vertices to set.
     */
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    /**
     * Retrieves the set of edges in the graph.
     * @return Set of edges.
     */
    public Set<Edge> getEdges() {
        return edges;
    }

    /**
     * Sets the set of edges in the graph.
     * @param edges The set of edges to set.
     */
    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex The vertex to add.
     */
    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    /**
     * Removes a vertex and all edges associated with it.
     * @param vertex The vertex to remove.
     */
    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.equals(vertex.getStartEdge()) || edge.equals(vertex.getEndEdge()));
    }

    /**
     * Adds an edge to the graph and creates vertices if needed.
     * @param edge The edge to add.
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
        for (Edge currEdge : this.edges) {
            if (edge.getLabel().equals(currEdge.getLabel())) {
                continue;
            }
            this.addVertex(new Vertex(currEdge, edge));
            this.addVertex(new Vertex(edge, currEdge));
        }
    }

    /**
     * Removes an edge from the graph and any vertices associated with it.
     * @param edge The edge to remove.
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
        vertices.removeIf(vertex -> vertex.getStartEdge().equals(edge) || vertex.getEndEdge().equals(edge));
    }

    /**
     * Checks whether the graph contains a specific vertex.
     * @param vertex The vertex to check.
     * @return True if the vertex exists in the graph, false otherwise.
     */
    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    /**
     * Checks whether the graph contains a specific edge.
     * @param edge The edge to check.
     * @return True if the edge exists in the graph, false otherwise.
     */
    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    /**
     * Gets the number of vertices in the graph.
     * @return The number of vertices.
     */
    public int vertexCount() {
        return vertices.size();
    }

    /**
     * Gets the number of edges in the graph.
     * @return The number of edges.
     */
    public int edgeCount() {
        return edges.size();
    }

    /**
     * Finds all neighboring vertices connected to a given vertex.
     * @param vertex The vertex for which neighbors are to be found.
     * @return List of neighboring vertices.
     */
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.equals(vertex.getStartEdge()) || edge.equals(vertex.getEndEdge())) {
                for (Vertex v : vertices) {
                    if (v != vertex && (v.getStartEdge() == edge || v.getEndEdge() == edge)) {
                        neighbors.add(v);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Clears the graph by removing all vertices and edges.
     */
    public void clear() {
        vertices.clear();
        edges.clear();
    }

    /**
     * Displays the graph by printing its vertices and edges to the console.
     */
    public void display() {
        System.out.println("Vertices:");
        for (Vertex vertex : vertices) {
            System.out.println("- Vertex with Value: " + vertex.getValue());
        }

        System.out.println("Edges:");
        for (Edge edge : edges) {
            System.out.println("- Edge with Label: " + edge.getLabel() + " at (" + edge.getxCoordinate() + ", " + edge.getyCoordinate() + ")");
        }
    }
}