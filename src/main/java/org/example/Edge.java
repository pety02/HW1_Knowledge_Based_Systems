package org.example;

import org.jgrapht.graph.DefaultEdge;

/**
 * @class Edge
 * @brief Represents an edge in a graph with coordinates.
 *
 * This class extends the DefaultEdge class and adds functionality to store
 * the x and y coordinates of an edge in the graph.
 */
public class Edge extends DefaultEdge {

    /**
     * @brief The x coordinate of the graph edge.
     */
    private int xCoordinate;

    /**
     * @brief The y coordinate of the graph edge.
     */
    private int yCoordinate;

    /**
     * @brief Constructs an Edge object with the specified x and y coordinates.
     *
     * This constructor initializes the edge with the given coordinates.
     *
     * @param xCoordinate The x coordinate of the edge.
     * @param yCoordinate The y coordinate of the edge.
     */
    public Edge(int xCoordinate, int yCoordinate) {
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);
    }

    /**
     * @brief Gets the x coordinate of the edge.
     *
     * @return The x coordinate of the edge.
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * @brief Sets the x coordinate of the edge.
     *
     * @param xCoordinate The new x coordinate for the edge.
     */
    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * @brief Gets the y coordinate of the edge.
     *
     * @return The y coordinate of the edge.
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * @brief Sets the y coordinate of the edge.
     *
     * @param yCoordinate The new y coordinate for the edge.
     */
    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}