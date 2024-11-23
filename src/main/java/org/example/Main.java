package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to solve the Traveling Salesman Problem (TSP) using heuristic methods.
 */
public class Main {

    /**
     * Calculates the heuristic distance between two cities using their coordinates.
     * The heuristic is the Euclidean distance between the cities.
     *
     * @param currentCity The starting city represented by an Edge object.
     * @param destinationCity The destination city represented by an Edge object.
     * @return The calculated distance. Returns 0 if both cities are the same.
     */
    public static double calculateHeuristic(Edge currentCity, Edge destinationCity) {
        return currentCity.equals(destinationCity) ? 0 : Math.sqrt(
                Math.pow((destinationCity.getxCoordinate() - currentCity.getxCoordinate()), 2)
                        + Math.pow((destinationCity.getyCoordinate() - currentCity.getyCoordinate()), 2));
    }

    /**
     * Calculates the total distance by summing the current distance and the heuristic distance
     * to the destination city.
     *
     * @param currentCity The starting city represented by an Edge object.
     * @param destinationCity The destination city represented by an Edge object.
     * @param currentDistance The current accumulated distance.
     * @return The updated total distance.
     */
    public static double calculateTotalDistance(Edge currentCity, Edge destinationCity, double currentDistance) {
        return currentDistance + calculateHeuristic(currentCity, destinationCity);
    }

    /**
     * Solves the Traveling Salesman Problem (TSP) for the given graph and starting city.
     * It finds the shortest path that visits all cities and returns to the starting city.
     *
     * @param graph The graph representing the cities and their connections.
     * @param startCity The starting city for the TSP, represented by an Edge object.
     * @return A string describing the best path and total distance.
     */
    public static String solveTSP(Graph graph, Edge startCity) {
        StringBuilder path = new StringBuilder(); ///< Tracks the sequence of cities visited.
        Set<Edge> visited = new HashSet<>(); ///< Tracks visited cities to avoid revisits.
        List<Edge> edges = new ArrayList<>(graph.getEdges()); ///< List of edges for traversal.

        Edge currentCity = startCity;
        visited.add(currentCity);

        double totalDistance = 0;

        while (visited.size() < graph.edgeCount()) {
            Edge nearestCity = null;
            double minDistance = Double.MAX_VALUE;
            // Find the nearest unvisited city
            for (Edge edge : edges) {
                if (!visited.contains(edge)) {
                    double distance = calculateTotalDistance(currentCity, edge, totalDistance);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestCity = edge;
                    }
                }
            }

            // Visit the nearest city if found
            if (nearestCity != null) {
                visited.add(nearestCity);
                totalDistance += minDistance;
                if (path.isEmpty()) {
                    path.append(nearestCity.getLabel());
                } else {
                    path.append(" -> ").append(nearestCity.getLabel());
                }
                currentCity = nearestCity;
            } else {
                break; // No more unvisited cities
            }
        }

        // Return to the starting city to complete the cycle
        totalDistance += calculateTotalDistance(currentCity, startCity, totalDistance);
        path.append(" -> ").append(startCity.getLabel());

        return "Path: " + path + "\nTotal Distance: " + Math.round(totalDistance) + " km.";
    }

    /**
     * The main entry point of the program. It initializes the graph, adds cities (nodes),
     * and solves the TSP for several starting cities, printing the results.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize the graph with cities and their coordinates
        Graph graph = new Graph();
        graph.addEdge(new Edge("Arad", 91, 492));
        graph.addEdge(new Edge("Bucharest", 400, 327));
        graph.addEdge(new Edge("Craiova", 253, 288));
        graph.addEdge(new Edge("Drobeta", 165, 299));
        graph.addEdge(new Edge("Eforie", 562, 293));
        graph.addEdge(new Edge("Fagaras", 305, 449));
        graph.addEdge(new Edge("Giurgiu", 375, 270));
        graph.addEdge(new Edge("Hirsova", 534, 350));
        graph.addEdge(new Edge("Iasi", 473, 506));
        graph.addEdge(new Edge("Lugoj", 165, 379));
        graph.addEdge(new Edge("Mehadia", 168, 339));
        graph.addEdge(new Edge("Neamt", 406, 537));
        graph.addEdge(new Edge("Oradea", 131, 571));
        graph.addEdge(new Edge("Pitesti", 320, 368));
        graph.addEdge(new Edge("Rimnicu", 233, 410));
        graph.addEdge(new Edge("Sibiu", 207, 457));
        graph.addEdge(new Edge("Timisoara", 94, 410));
        graph.addEdge(new Edge("Urziceni", 456, 350));
        graph.addEdge(new Edge("Valui", 509, 444));
        graph.addEdge(new Edge("Zerind", 108, 531));

        // Solve the TSP starting from different cities and print the results
        String bestPathFromArad = solveTSP(graph, new Edge("Arad", 91, 492));
        System.out.println(bestPathFromArad);

        String bestPathFromFagaras = solveTSP(graph, new Edge("Fagaras", 305, 449));
        System.out.println(bestPathFromFagaras);

        String bestPathFromRimnicu = solveTSP(graph, new Edge("Rimnicu", 233, 410));
        System.out.println(bestPathFromRimnicu);
    }
}