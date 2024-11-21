package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.*;

public class Main {
    public static double[][] calculateHeuristics(Graph<String, Edge> graph) {
        final int size = graph.edgeSet().size(); // gets the max size of the graph cities
        double maxHeuristic = Double.MAX_VALUE; // initializes max heuristic
        double[][] heuristics = new double[size][size]; // initializes the heuristics array

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i == j) {
                    // if the start and end city are the same, the heuristic tends to infinity
                    heuristics[i][j] = maxHeuristic;
                    continue;
                }
                // gets the start edge nad end edge
                Edge startEdge = graph.edgeSet().stream().toList().get(i);
                Edge endEdge = graph.edgeSet().stream().toList().get(j);

                // calculates the x and y coordinates between start and end edges
                int vectorXCoordinate = endEdge.getxCoordinate() - startEdge.getxCoordinate();
                int vectorYCoordinate = endEdge.getyCoordinate() - startEdge.getyCoordinate();

                // calculates the distance between start and end edges and sets this value as
                // heuristic for cities with i-th and j-th index (the start and the end city)
                double currentDistance = Math.sqrt(Math.pow(vectorXCoordinate, 2) + Math.pow(vectorYCoordinate, 2));
                heuristics[i][j] = currentDistance;
            }
        }

        return heuristics;
    }

    public static String solveTSP(Graph<String, Edge> graph, String startCity) {
        StringBuilder path = new StringBuilder(startCity); // Start from the initial city
        var cities = graph.vertexSet().stream().toList(); // Convert the vertex set to a List of String
        List<String> visited = new ArrayList<>(); // Track visited cities in a List of String
        visited.add(startCity);

        // Precompute heuristics using the provided method
        double[][] heuristics = calculateHeuristics(graph);
        int currentCityIndex = cities.indexOf(startCity);

        while (visited.size() < cities.size()) {
            int nearestCityIndex = -1;
            double shortestDistance = Double.MAX_VALUE;

            // Find the nearest unvisited city
            for (int i = 0; i < cities.size(); i++) {
                if (i == currentCityIndex || visited.contains(cities.get(i))) {
                    continue; // Skip the current city or already visited ones
                }
                if (heuristics[currentCityIndex][i] < shortestDistance) {
                    shortestDistance = heuristics[currentCityIndex][i];
                    nearestCityIndex = i;
                }
            }

            // If there is no more cities to be visited, terminate the loop
            if(nearestCityIndex == -1) {
                break;
            }

            // Update the path and mark the city as visited
            String nearestCity = cities.get(nearestCityIndex);
            path.append(" -> ").append(nearestCity);

            visited.add(nearestCity);
            currentCityIndex = nearestCityIndex;
        }

        // Return to the starting city
        path.append(" -> ").append(startCity);
        return path.toString();
    }

    public static Graph<String, Edge> getFullyConnectedGraph(Map<String, Edge> map) {
        // Create a graph
        Graph<String, Edge> graph = new DefaultDirectedGraph<>(Edge.class);

        // Add vertices (cities)
        map.keySet().forEach(graph::addVertex);

        // Add edges to a fully connected graph
        for (String cityA : map.keySet()) {
            for (String cityB : map.keySet()) {
                if (!cityA.equals(cityB)) {
                    Edge coordinatesA = map.get(cityA);
                    Edge coordinatesB = map.get(cityB);

                    // Add an edge between the cities with definite coordinates
                    Edge edge = new Edge(coordinatesB.getxCoordinate() - coordinatesA.getxCoordinate(),
                            coordinatesB.getyCoordinate() - coordinatesA.getyCoordinate());
                    graph.addEdge(cityA, cityB, edge);
                }
            }
        }

        return graph;
    }

    public static void main(String[] args) {

        // Define the coordinates for each city
        Map<String, Edge> romaniaMap = new HashMap<>();
        romaniaMap.put("Arad", new Edge(91, 492));
        romaniaMap.put("Bucharest", new Edge(400, 327));
        romaniaMap.put("Craiova", new Edge(253, 288));
        romaniaMap.put("Drobeta", new Edge(165, 299));
        romaniaMap.put("Eforie", new Edge(562, 293));
        romaniaMap.put("Fagaras", new Edge(305, 449));
        romaniaMap.put("Giurgiu", new Edge(375, 270));
        romaniaMap.put("Hirsova", new Edge(534, 350));
        romaniaMap.put("Iasi", new Edge(473, 506));
        romaniaMap.put("Lugoj", new Edge(165, 379));
        romaniaMap.put("Mehadia", new Edge(168, 339));
        romaniaMap.put("Neamt", new Edge(406, 537));
        romaniaMap.put("Oradea", new Edge(131, 571));
        romaniaMap.put("Pitesti", new Edge(320, 368));
        romaniaMap.put("Rimnicu", new Edge(233, 410));
        romaniaMap.put("Sibiu", new Edge(207, 457));
        romaniaMap.put("Timisoara", new Edge(94, 410));
        romaniaMap.put("Urziceni", new Edge(456, 350));
        romaniaMap.put("Vaslui", new Edge(509, 444));
        romaniaMap.put("Zerind", new Edge(108, 531));

        // Gets fully connected graph from the map of Romania
        Graph<String, Edge> romaniaGraph = getFullyConnectedGraph(romaniaMap);

        String bestPathFromArad = solveTSP(romaniaGraph, "Arad");
        System.out.println(bestPathFromArad);
        String bestPathFromFagaras = solveTSP(romaniaGraph, "Fagaras");
        System.out.println(bestPathFromFagaras);
        String bestPathFromRimnicu = solveTSP(romaniaGraph, "Rimnicu");
        System.out.println(bestPathFromRimnicu);
    }
}