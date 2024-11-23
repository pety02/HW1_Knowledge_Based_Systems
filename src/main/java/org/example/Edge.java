package org.example;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {
    private String label;
    private int xCoordinate;
    private int yCoordinate;

    public Edge(String label, int xCoordinate, int yCoordinate) {
        this.setLabel(label);
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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