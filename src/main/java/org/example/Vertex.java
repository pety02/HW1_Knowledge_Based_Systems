package org.example;

public class Vertex {
    private Edge startEdge;
    private Edge endEdge;
    private double value;

    public Vertex(Edge startEdge, Edge endEdge) {
        double value = Math.sqrt(Math.pow((endEdge.getyCoordinate() - startEdge.getyCoordinate()), 2) + Math.pow((endEdge.getxCoordinate() - startEdge.getxCoordinate()), 2));
        this.setStartEdge(startEdge);
        this.setEndEdge(endEdge);
        this.setValue(value);
    }

    public Edge getStartEdge() {
        return startEdge;
    }

    public void setStartEdge(Edge startEdge) {
        this.startEdge = startEdge;
    }

    public Edge getEndEdge() {
        return endEdge;
    }

    public void setEndEdge(Edge endEdge) {
        this.endEdge = endEdge;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
