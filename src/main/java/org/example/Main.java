package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Main {
    double[][] calculateHeuristics(Graph<String, Edge> graph) {
        final int size = graph.edgeSet().size();
        int maxHeuristic = Integer.MAX_VALUE;
        double[][] heuristics = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i == j) {
                    heuristics[i][j] = maxHeuristic;
                    continue;
                }
                var firstPoint = graph.edgeSet().stream().toList().get(i);
                var secondPoint = graph.edgeSet().stream().toList().get(j);
                int vectorXCoordinate = secondPoint.getxCoordinate() - firstPoint.getxCoordinate();
                int vectorYCoordinate = secondPoint.getyCoordinate() - firstPoint.getyCoordinate();
                double currentDistance = Math.sqrt(Math.pow(vectorXCoordinate, 2) + Math.pow(vectorYCoordinate, 2));
                heuristics[i][j] = currentDistance;
            }
        }

        return heuristics;
    }
    String solveTSP(Graph<String, Edge> graph, String startCity, String endCity) {
        double[][] heuristics = calculateHeuristics(graph);

    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}