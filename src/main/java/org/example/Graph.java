package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private List<Vertex> vertices; // List of vertices in the graph
    private Set<Edge> edges;       // Set of edges in the graph

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new HashSet<>();
    }

    // Constructor with initial vertices and edges
    public Graph(List<Vertex> vertices, Set<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    // Get vertices
    public List<Vertex> getVertices() {
        return vertices;
    }

    // Set vertices
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    // Get edges
    public Set<Edge> getEdges() {
        return edges;
    }

    // Set edges
    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    // Add a vertex
    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    // Remove a vertex and all its associated edges
    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);

        // Remove edges associated with the vertex
        edges.removeIf(edge -> edge.equals(vertex.getStartEdge()) || edge.equals(vertex.getEndEdge()));
    }

    // Add an edge
    public void addEdge(Edge edge) {
        edges.add(edge);
        for (Edge currEdge : this.edges) {
            if(edge.getLabel().equals(currEdge.getLabel())) {
                continue;
            }
            this.addVertex(new Vertex(currEdge, edge));
            this.addVertex(new Vertex(edge, currEdge));
        }
    }

    // Remove an edge
    public void removeEdge(Edge edge) {
        edges.remove(edge);

        // Removes vertices associated with the edge
        vertices.removeIf(vertex -> vertex.getStartEdge().equals(edge) || vertex.getEndEdge().equals(edge));
    }

    // Check if the graph contains a vertex
    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    // Check if the graph contains an edge
    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    // Get the size of the graph (number of vertices)
    public int vertexCount() {
        return vertices.size();
    }

    // Get the size of the graph (number of edges)
    public int edgeCount() {
        return edges.size();
    }

    // Find all neighbors of a vertex (connected vertices)
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

    // Clear the graph (remove all vertices and edges)
    public void clear() {
        vertices.clear();
        edges.clear();
    }

    // Display the graph (vertices and edges)
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