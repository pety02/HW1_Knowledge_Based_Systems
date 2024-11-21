package org.example;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {
    private int xCoordinate; // the x coordinate of any graph edge
    private int yCoordinate; // the y coordinate of any graph edge

    // Constructs the Edge object by its xCoordinate and yCoordinate
    public Edge(int xCoordinate, int yCoordinate) {
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);
    }

    // Getters and Setters for the x and y coordinates
    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() { return yCoordinate; }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}