### **Описание на задачата**:
Търговският пътник трябва да тръгне от определен начален град и да се върне в него, 
като посети точно по веднъж всички останали градове от зададената област. Целта е 
да се намери най-късият маршрут (маршрутът с минимална дължина) на търговския 
пътник. Зададено е множество от селища в Румъния с техните локации, като се 
предполага пълна свързаност.
    
    romania_map.locations = dict(
        Arad=(91, 492), Bucharest=(400, 327), Craiova=(253, 288),
        Drobeta=(165, 299), Eforie=(562, 293), Fagaras=(305, 449),
        Giurgiu=(375, 270), Hirsova=(534, 350), Iasi=(473, 506),
        Lugoj=(165, 379), Mehadia=(168, 339), Neamt=(406, 537),
        Oradea=(131, 571), Pitesti=(320, 368), Rimnicu=(233, 410),
        Sibiu=(207, 457), Timisoara=(94, 410), Urziceni=(456, 350),
        Vaslui=(509, 444), Zerind=(108, 531)
    )

!["Картата на Румъния"]("C:\Users\User\Desktop\romania_map.png")

Задачата е да проектираме и реализираме програма, която да намира добро приближение на най-краткия маршрут на търговския пътник, като използваме изчислените разстояния между всяка двойка градове в напълно свързания фраф от градовете в Румъния.
### **Oписание на предложения/използвания метод за решаване на задачата**:
Алгоритъмът, който избрах да имплементирам лежи на евристичната функция за "най-добрия съседен град" (определя се от минималното разстояние от начален до краен град по права линия - геометрични изчисления). Чрез функцията calculateHeuristics изчислявам евристичните функции (разстоянията по права линия между всеки два града) като ги представям в табличен вариант (двумерен масив от double стойности). След което при наличието на така изчислените евристики, минавам към съществения алгоритъм за намиране на най-добро приближение на най-добрия път, започващ от фиксиран град, по картата на Румъния и приключващ в същия геад (като през всеки град, различен от началния град, мимавам по точно веднъж).
- Пример за работата на избрания от мен алгоритъм:
!["Малък пример за работата на избрания от мен алгоритъм"]("C:\Users\User\Desktop\heuristics_example.png")
### **Описание на реализацията с псевдокод**:
```pseudo
CLASS Edge EXTENDS DefaultEdge
    xCoordinate : INTEGER      
    yCoordinate : INTEGER  

    FUNCTION Constructor(xCoordinate : INTEGER, yCoordinate : INTEGER)
        SET this.xCoordinate TO xCoordinate
        SET this.yCoordinate TO yCoordinate
    END FUNCTION

    FUNCTION getXCoordinate() : INTEGER
        RETURN xCoordinate
    END FUNCTION

    FUNCTION getYCoordinate() : INTEGER
        RETURN yCoordinate
    END FUNCTION

    FUNCTION setXCoordinate(xCoordinate : INTEGER)
        SET this.xCoordinate TO xCoordinate
    END FUNCTION

    FUNCTION setYCoordinate(yCoordinate : INTEGER)
        SET this.yCoordinate TO yCoordinate
    END FUNCTION
END CLASS

CLASS Main 
    FUNCTION calculateHeuristics(graph : Graph OF STRING AND Edge) : STATIC 2D ARRAY OF DOUBLE 
        SET size : final int TO graph.edgeSet().size()
        SET maxHeuristics : double TO Double.MAX_VALUE
        SET heuristices : double[][] TO new double[size][size]

        FOR SET i : int TO 0; CHECK i < size; INCREMENT i
            FOR SET j : int TO 0; CHECK j < size; INCREMENT j
                IF i == j 
                    SET heuristics[i][j] : double TO maxHeuristic
                    CONTINUE
                END IF
                
                SET startEdge : Edge TO graph.edgeSet().stream().toList().get(i)
                SET endEdge : Edge TO graph.edgeSet().stream().toList().get(j)

                SET vectorXCoordinate : int TO endEdge.getXCoordinate() - startEdge.getXCoordinate();
                SET vectorYCoordinate : int TO endEdge.getYCoordinate() - startEdge.getYCoordinate();
                SET currentDistance : double TO Math.sqrt(Math.pow(vectorXCoordinate, 2) + Math.pow(vectorYCoordinate, 2));
                SET heuristics[i][j] TO currentDistance;
            END FOR
        END FOR
    END FUNCTION

    FUNCTION solveTSP(graph : GRAPH<STRING, Edge>, startCity : STRING) : STRING
        path : STRING = startCity

        cities : LIST<STRING> = CONVERT_TO_LIST(graph.vertexSet())
        visited : LIST<STRING> = EMPTY LIST
        ADD startCity TO visited

        heuristics : MATRIX<DOUBLE> = calculateHeuristics(graph)
        currentCityIndex : INTEGER = INDEX_OF(startCity, cities)

        WHILE SIZE(visited) < SIZE(cities)
            nearestCityIndex : INTEGER = -1
            shortestDistance : DOUBLE = MAX_VALUE

            FOR i FROM 0 TO SIZE(cities) - 1
                IF i == currentCityIndex OR cities[i] IN visited
                    CONTINUE
                END IF

                IF heuristics[currentCityIndex][i] < shortestDistance
                    shortestDistance = heuristics[currentCityIndex][i]
                    nearestCityIndex = i
                END IF
            END FOR

            IF nearestCityIndex == -1
                BREAK
            END IF

            nearestCity : STRING = cities[nearestCityIndex]
            path = CONCATENATE(path, " -> ", nearestCity)

            ADD nearestCity TO visited
            currentCityIndex = nearestCityIndex
        END WHILE

        path = CONCATENATE(path, " -> ", startCity)
        RETURN path
    END FUNCTION

    FUNCTION getFullyConnectedGraph(map : MAP<STRING, Edge>) : GRAPH OF STRING AND Edge
        graph : GRAPH<STRING, Edge> = NEW DirectedGraph()

        FOR EACH city IN KEYS(map)
            ADD_VERTEX(graph, city)
        END FOR

        FOR EACH cityA IN KEYS(map)
            FOR EACH cityB IN KEYS(map)
                IF cityA != cityB
                    coordinatesA : Edge = map[cityA]
                    coordinatesB : Edge = map[cityB]

                    edge : Edge = NEW Edge(
                        coordinatesB.xCoordinate - coordinatesA.xCoordinate,
                        coordinatesB.yCoordinate - coordinatesA.yCoordinate
                    )

                    ADD_EDGE(graph, cityA, cityB, edge)
                END IF
            END FOR
        END FOR

        RETURN graph
    END FUNCTION


    FUNCTION main(args : ARRAY OF STRING)
        romaniaMap : MAP<STRING, Edge> = EMPTY MAP
        romaniaMap["Arad"] = NEW Edge(91, 492)
        romaniaMap["Bucharest"] = NEW Edge(400, 327)
        romaniaMap["Craiova"] = NEW Edge(253, 288)
        romaniaMap["Drobeta"] = NEW Edge(165, 299)
        romaniaMap["Eforie"] = NEW Edge(562, 293)
        romaniaMap["Fagaras"] = NEW Edge(305, 449)
        romaniaMap["Giurgiu"] = NEW Edge(375, 270)
        romaniaMap["Hirsova"] = NEW Edge(534, 350)
        romaniaMap["Iasi"] = NEW Edge(473, 506)
        romaniaMap["Lugoj"] = NEW Edge(165, 379)
        romaniaMap["Mehadia"] = NEW Edge(168, 339)
        romaniaMap["Neamt"] = NEW Edge(406, 537)
        romaniaMap["Oradea"] = NEW Edge(131, 571)
        romaniaMap["Pitesti"] = NEW Edge(320, 368)
        romaniaMap["Rimnicu"] = NEW Edge(233, 410)
        romaniaMap["Sibiu"] = NEW Edge(207, 457)
        romaniaMap["Timisoara"] = NEW Edge(94, 410)
        romaniaMap["Urziceni"] = NEW Edge(456, 350)
        romaniaMap["Vaslui"] = NEW Edge(509, 444)
        romaniaMap["Zerind"] = NEW Edge(108, 531)

        romaniaGraph : GRAPH<STRING, Edge> = getFullyConnectedGraph(romaniaMap)

        bestPathFromArad : STRING = solveTSP(romaniaGraph, "Arad")
        PRINT bestPathFromArad

        bestPathFromFagaras : STRING = solveTSP(romaniaGraph, "Fagaras")
        PRINT bestPathFromFagaras

        bestPathFromRimnicu : STRING = solveTSP(romaniaGraph, "Rimnicu")
        PRINT bestPathFromRimnicu
    END FUNCTION
END CLASS
```
### **Самата реализация като изходен код на предпочитан език за програмиране (JAVA Maven проект)**:
- Edge class
```java
package org.example;
import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

    private int xCoordinate;

    private int yCoordinate;

    public Edge(int xCoordinate, int yCoordinate) {
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
```
- Main class
```java
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
                // gets the start edge and end edge
                Edge startEdge = graph.edgeSet().stream().toList().get(i);
                Edge endEdge = graph.edgeSet().stream().toList().get(j);

                // calculates the x and y coordinates between start and end edges
                int vectorXCoordinate = endEdge.getXCoordinate() - startEdge.getXCoordinate();
                int vectorYCoordinate = endEdge.getYCoordinate() - startEdge.getYCoordinate();

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

            // If there are no more cities to be visited, terminate the loop
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
                    Edge edge = new Edge(coordinatesB.getXCoordinate() - coordinatesA.getXCoordinate(),
                            coordinatesB.getYCoordinate() - coordinatesA.getYCoordinate());
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

        // Finding the best paths from different cities in Romania and prints them
        String bestPathFromArad = solveTSP(romaniaGraph, "Arad");
        System.out.println(bestPathFromArad);
        String bestPathFromFagaras = solveTSP(romaniaGraph, "Fagaras");
        System.out.println(bestPathFromFagaras);
        String bestPathFromRimnicu = solveTSP(romaniaGraph, "Rimnicu");
        System.out.println(bestPathFromRimnicu);
    }
}
```
- GitHub на проекта:
```bash
git clone https://github.com/pety02/HW1_Knowledge_Based_Systems.git
```

### **Инструкции за компилиране на програмата**:
- при клониране на проекта от GitHub:
    1. Уверете се, че Maven и Java са инсталирани:
        
        1.1. Проверете версията на Maven:
        ```bash
        mvn -v
        ```
        1.2. Проверете версията на Java:
        ```bash
        java -version
        ```
    2. Клонирайте репозиторито:
    ```bash
    git clone https://github.com/pety02/HW1_Knowledge_Based_Systems.git
    ```
    3. Влезте в директорията на проекта:
    ```bash
    cd HW1_Knowledge_Based_Systems
    ```
    3. Извършете компилация с Maven:
    ```bash
    mvn clean compile
    ```
    - Това ще:
        1. Почисти временните файлове.
        2. Компилира Java кода.
        3. Ако искате да стартирате тестовете:
- при наличието на source кода и конфигурационен pom.xml файл, които са в една директория (тоест не следват стандартите на Maven):
    - Проблем: Maven очаква специфична структура на директориите. Ако структурата на проекта е различна, трябва да направите промени в pom.xml или да организирате файловете правилно.
        1. Вариант 1: Промяна на структурата на проекта към Maven стандарт
            1.1. Организирайте файловете според Maven стандартната структура:

            !["Структурата на Java Maven проект"]("C:\Users\User\Desktop\java-mvn-project-structure.png")
            
            1.2. Преместете файловете:
            - Всички .java файлове трябва да бъдат в src/main/java.
            - Всички ресурси като .properties, .xml, и др. трябва да бъдат в src/main/resources. 
            1.3. Компилирайте:
            ```bash
            mvn clean compile
            ```
        2. Вариант 2: Адаптиране на pom.xml за текущата структура Ако не искате да местите файловете, добавете следните настройки в pom.xml, за да укажете текущите пътища:
            2.1. Добавете или променете <build> секцията:
            ```xml
            <build>
                <sourceDirectory>път/до/java/файловете</sourceDirectory>
                <testSourceDirectory>път/до/тестовите/файлове</testSourceDirectory>
                <resources>
                    <resource>
                        <directory>път/до/ресурсите</directory>
                    </resource>
                </resources>
                <testResources>
                    <testResource>
                        <directory>път/до/ресурсите/за/тестове</directory>
                    </testResource>
                </testResources>
            </build>
            ```
            2.2. Компилирайте проекта:
            ```bash
            mvn clean compile
            ```
            2.3. Създайте JAR файл:
            ```bash
            mvn package
            ```
### **Примерни резултати**:
- След изпълнението на първия тест, посочен в main метода на Main класа, би трябвало да се изведе:
    Arad -> Drobeta -> Fagaras -> Lugoj -> Giurgiu -> Sibiu -> Eforie -> Zerind -> Bucharest -> Craiova -> Iasi -> Hirsova -> Neamt -> Mehadia -> Rimnicu -> Oradea -> Urziceni -> Pitesti -> Timisoara -> Vaslui -> Arad

- След изпълнението на втория тест, посочен в main метода на Main класа, би трябвало да се изведе:
    Fagaras -> Drobeta -> Arad -> Timisoara -> Eforie -> Sibiu -> Lugoj -> Giurgiu -> Urziceni -> Oradea -> Rimnicu -> Pitesti -> Vaslui -> Zerind -> Bucharest -> Craiova -> Iasi -> Hirsova -> Neamt -> Mehadia -> Fagaras

- След изпълнението на трвтия тест, посочен в main метода на Main класа, би трябвало да се изведе:
    Rimnicu -> Oradea -> Urziceni -> Pitesti -> Fagaras -> Drobeta -> Arad -> Timisoara -> Eforie -> Sibiu -> Lugoj -> Giurgiu -> Mehadia -> Neamt -> Hirsova -> Craiova -> Iasi -> Bucharest -> Zerind -> Vaslui -> Rimnicu

    - анализ на изходните данни при изпълнение на алгоритъма:
    В обобщение на получените резултати бихме могли да кажем, че в зависимост от кой град в Румъния тръгваме различни маршрути за обход на посочената област биха били най-подходящи, тъй като евристиките биха били по-различни, тоест ще виждаме различни (оптимални според случая) маршрути. Резултатите, които Ви се визуализират доказват тези ми твърдения като Ви демонстрират обход на зададената област с един и същ алгоритъм, но преизчислени евристики (според ситуацията).
