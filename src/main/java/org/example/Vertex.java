package org.example;

/**
 * Represents a vertex in a graph, defined by its connection to two edges and a calculated value.
 */
public class Vertex {
    private Edge startEdge; ///< The starting edge associated with the vertex.
    private Edge endEdge; ///< The ending edge associated with the vertex.
    private double value; ///< The value of the vertex, calculated based on the coordinates of its edges.

    /**
     * Constructs a Vertex using two edges and calculates its value.
     * The value is computed as the Euclidean distance between the coordinates of the start and end edges.
     * @param startEdge The starting edge of the vertex.
     * @param endEdge The ending edge of the vertex.
     */
    public Vertex(Edge startEdge, Edge endEdge) {
        double value = Math.sqrt(Math.pow((endEdge.getyCoordinate() - startEdge.getyCoordinate()), 2)
                + Math.pow((endEdge.getxCoordinate() - startEdge.getxCoordinate()), 2));
        this.setStartEdge(startEdge);
        this.setEndEdge(endEdge);
        this.setValue(value);
    }

    /**
     * Retrieves the starting edge of the vertex.
     * @return The starting edge.
     */
    public Edge getStartEdge() {
        return startEdge;
    }

    /**
     * Sets the starting edge of the vertex.
     * @param startEdge The edge to set as the starting edge.
     */
    public void setStartEdge(Edge startEdge) {
        this.startEdge = startEdge;
    }

    /**
     * Retrieves the ending edge of the vertex.
     * @return The ending edge.
     */
    public Edge getEndEdge() {
        return endEdge;
    }

    /**
     * Sets the ending edge of the vertex.
     * @param endEdge The edge to set as the ending edge.
     */
    public void setEndEdge(Edge endEdge) {
        this.endEdge = endEdge;
    }

    /**
     * Retrieves the value of the vertex.
     * @return The value of the vertex.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the vertex.
     * @param value The value to set for the vertex.
     */
    public void setValue(double value) {
        this.value = value;
    }
}