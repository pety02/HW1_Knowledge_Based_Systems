package org.example;

import org.jgrapht.graph.DefaultEdge;

/**
 * Represents an edge in a graph with additional attributes such as label
 * and coordinates. This class extends the DefaultEdge class from the JGraphT
 * library and adds functionality for managing a label and coordinates (x, y).
 */
public class Edge extends DefaultEdge {
    private String label; /// The label associated with the edge.
    private int xCoordinate; /// The x-coordinate associated with the edge.
    private int yCoordinate; /// The y-coordinate associated with the edge.

    /**
     * Constructs an Edge object with a specified label and coordinates.
     * @param label The label of the edge.
     * @param xCoordinate The x-coordinate of the edge.
     * @param yCoordinate The y-coordinate of the edge.
     */
    public Edge(String label, int xCoordinate, int yCoordinate) {
        this.setLabel(label);
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
    }

    /**
     * Retrieves the label of the edge.
     * @return The label of the edge.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the edge.
     * @param label The new label for the edge.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Retrieves the x-coordinate of the edge.
     * @return The x-coordinate of the edge.
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate of the edge.
     * @param xCoordinate The new x-coordinate for the edge.
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Retrieves the y-coordinate of the edge.
     * @return The y-coordinate of the edge.
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate of the edge.
     * @param yCoordinate The new y-coordinate for the edge.
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
