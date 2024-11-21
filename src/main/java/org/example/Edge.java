package org.example;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {
    private int xCoordinate;
    private int yCoordinate;

    public Edge(int xCoordinate, int yCoordinate) {
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}