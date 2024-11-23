package org.example;
import java.util.*;

public class Main {

    public static double calculateHeuristic(Edge currentCity, Edge destinationCity) {
        return currentCity.equals(destinationCity) ? 0 : Math.sqrt(
                Math.pow((destinationCity.getxCoordinate() - currentCity.getxCoordinate()), 2)
                        + Math.pow((destinationCity.getyCoordinate() - currentCity.getyCoordinate()), 2));
    }

    public static double calculateTotalDistance(Edge currentCity, Edge destinationCity, double currentDistance) {
        return currentDistance + calculateHeuristic(currentCity, destinationCity);
    }

    public static String solveTSP(Graph graph, Edge startCity) {
        StringBuilder path = new StringBuilder(); // Start from the initial city
        Set<Edge> visited = new HashSet<>(); // Track visited cities in a Set for uniqueness
        List<Edge> edges = new ArrayList<>(graph.getEdges()); // List of edges for traversal

        Edge currentCity = startCity;
        visited.add(currentCity);
        //path.append(currentCity.getLabel());

        double totalDistance = 0;

        while (visited.size() < graph.edgeCount()) {
            Edge nearestCity = null;
            double minDistance = Double.MAX_VALUE;

            // Find the nearest unvisited city
            for (Edge edge : edges) {
                if (!visited.contains(edge)) {
                    double distance = calculateHeuristic(currentCity, edge);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestCity = edge;
                    }
                }
            }

            // If we find a nearest city, visit it
            if (nearestCity != null) {
                visited.add(nearestCity);
                totalDistance += minDistance;
                if(path.isEmpty()) {
                    path.append(nearestCity.getLabel());
                } else {
                    path.append(" -> ").append(nearestCity.getLabel());
                }
                currentCity = nearestCity;
            } else {
                break; // No unvisited cities left
            }
        }

        // Return to the start city to complete the cycle
        totalDistance += calculateHeuristic(currentCity, startCity);
        path.append(" -> ").append(startCity.getLabel());

        return "Path: " + path + "\nTotal Distance: " + Math.round(totalDistance) + " km.";
    }


    /**
     * @brief The main entry point of the program.
     *
     * This method initializes the map of cities with their coordinates, generates the graph, and solves the
     * TSP for various starting cities, printing the results.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Define the coordinates for each city
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


        // Gets fully connected graph from the map of Romania
        //Graph<String, Edge> romaniaGraph = getFullyConnectedGraph(romaniaMap);

        // Finding the best paths from different cities in Romania and prints them
        String bestPathFromArad = solveTSP(graph, new Edge("Arad", 91, 492));
        System.out.println(bestPathFromArad);
        String bestPathFromFagaras = solveTSP(graph, new Edge("Fagaras", 305, 449));
        System.out.println(bestPathFromFagaras);
        String bestPathFromRimnicu = solveTSP(graph, new Edge("Rimnicu", 233, 410));
        System.out.println(bestPathFromRimnicu);
    }
}