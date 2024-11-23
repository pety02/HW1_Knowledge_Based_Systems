# **Системи основани на знание**: Домашна работа № 1
## **Име**: Петя Личева
## **Факултетен номер**: 3MI0700022
## **Специалност**: Информационни системи
## **Курс**: 3

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

Задачата е да проектираме и реализираме програма, която да намира добро приближение на най-краткия маршрут на търговския пътник, като използваме изчислените разстояния между всяка двойка градове в напълно свързания граф от градовете в Румъния.
### **Oписание на предложения/използвания метод за решаване на задачата**:
Алгоритъмът, който избрах да имплементирам лежи на основата на евристичната функция за "най-добрия съседен град" (определя се от разстоянието от текущия до целевия град по права линия - геометрични изчисления). Чрез функцията **public static double calculateHeuristic(Edge currentCity, Edge destinationCity)** изчислявам в реално време евристичните функции (разстоянията по права линия между текущ и целеви град) при поискване и връщам стойност с плаваща запетая (double). След което при наличието на така изчислените евристики, минавам към съществения алгоритъм за намиране на най-добро приближение на най-добрия път, започващ от фиксиран град, по картата на Румъния и приключващ в същия геад (като през всеки град, различен от началния град, мимавам по точно веднъж).
- Пример за работата на избрания от мен алгоритъм:
!["Малък пример за работата на избрания от мен алгоритъм"]("C:\Users\User\Desktop\heuristics_example.png")
По този начин би работил алгоритъмът, който реших да имплментирам, ако използвах само евристиките (оценките на пътя от текущия град до целевия такъв). Имплементираният от мен алгоритъм надгражда този "наивен" подход като използва и досегашно изминатия път, приближавайки се идейно до информираният метод на търсене на алгоритъма за търсене - A*.
### **Описание на реализацията с псевдокод**:
За реализацията на този алгоритъм използвам **Custom имплементация** на **Edge**, **Vertex** и **Graph** класове, така че да пасват на условието на задачата, тоест **Edge обектите да имат label(име на град в Румъния), x и y координати**, по които да може да се изчислява евристиката и текущо изминатия път (необходими компоненти в реализацията на алгоритъм като този), а **Vertex обектите да представляват двойка от начална и крайна дестинация**. Няма да се фиксирам върху имплментацията на самия граф, тъй като тя е стандартната такава, но поставена в по-ясен контекст (при нужда може да я разгледата в **GitHub-а на проекта: https://github.com/pety02/HW1_Knowledge_Based_Systems)**. По-долу Ви поднасям **псевдокод на реализацията на функциите, участващи в самия алгоритъм**, а също и на main функцията (стартова точка на програмата).

```pseudo
CLASS MAIN
    FUNCTION calculateHeuristic(currentCity, destinationCity) : DOUBLE
        IF currentCity equals destinationCity THEN
            RETURN 0
        ELSE
            CALCULATE Euclidean distance between currentCity and destinationCity:
            distance = SQRT((destinationCity.x - currentCity.x)^2 + (destinationCity.y - currentCity.y)^2)
            RETURN distance
        END IF
    END FUNCTION

    FUNCTION RETURN currentDistance + calculateHeuristic(currentCity, destinationCity)
        RETURN currentDistance + calculateHeuristic(currentCity, destinationCity)
    END FUNCTION

    FUNCTION solveTSP(graph, startCity)
        INITIALIZE path as an empty string
        INITIALIZE visited as an empty set
        CONVERT graph's edges to a list and store in edges
        SET currentCity to startCity
        ADD currentCity to visited
        INITIALIZE totalDistance to 0

        WHILE visited size is less than the total number of edges in the graph:
            SET nearestCity to null
            SET minDistance to infinity

            FOR each edge in edges:
                IF edge is not in visited:
                    CALCULATE distance to edge using calculateHeuristic
                    IF distance is less than minDistance:
                        SET minDistance to distance
                        SET nearestCity to edge
                    END IF
                END IF
            END FOR

            IF nearestCity is not null THEN
                ADD nearestCity to visited
                INCREMENT totalDistance by minDistance
                IF path is empty THEN
                    ADD nearestCity label to path
                ELSE
                    APPEND " -> " and nearestCity label to path
                    SET currentCity to nearestCity
                END IF
            ELSE
                BREAK
            END IF

            RETURN to the starting city:
                INCREMENT totalDistance by calculateHeuristic(currentCity, startCity)
                APPEND " -> " and startCity label to path
        END WHILE

        RETURN "Path: " + path + "\nTotal Distance: " + totalDistance rounded to nearest integer
    END FUNCTION
END CLASS
```
### **Самата реализация като изходен код на предпочитан език за програмиране (JAVA Maven проект)**:
- **Edge class**
```java
package org.example;
import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {
    private String label; // The label associated with the edge.
    private int xCoordinate; // The x-coordinate associated with the edge.
    private int yCoordinate; // The y-coordinate associated with the edge.

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
```
- **Vertex class**
```java
package org.example;

public class Vertex {
    private Edge startEdge; // The starting edge associated with the vertex.
    private Edge endEdge; // The ending edge associated with the vertex.
    private double value; // The value of the vertex, calculated based on the coordinates of its edges.

    public Vertex(Edge startEdge, Edge endEdge) {
        double value = Math.sqrt(Math.pow((endEdge.getyCoordinate() - startEdge.getyCoordinate()), 2)
                + Math.pow((endEdge.getxCoordinate() - startEdge.getxCoordinate()), 2));
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
```
- **Graph class**
```java
package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private List<Vertex> vertices; // List of vertices in the graph.
    private Set<Edge> edges; // Set of edges in the graph.

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new HashSet<>();
    }

    public Graph(List<Vertex> vertices, Set<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.equals(vertex.getStartEdge()) || edge.equals(vertex.getEndEdge()));
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        for (Edge currEdge : this.edges) {
            if (edge.getLabel().equals(currEdge.getLabel())) {
                continue;
            }
            this.addVertex(new Vertex(currEdge, edge));
            this.addVertex(new Vertex(edge, currEdge));
        }
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
        vertices.removeIf(vertex -> vertex.getStartEdge().equals(edge) || vertex.getEndEdge().equals(edge));
    }

    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public int vertexCount() {
        return vertices.size();
    }

    public int edgeCount() {
        return edges.size();
    }

    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.equals(vertex.getStartEdge()) || edge.equals(vertex.getEndEdge())) {
                for (Vertex v : vertices) {
                    if (v != vertex && (v.getStartEdge() == edge || v.getEndEdge() == edge)) {
                        neighbors.add(v);
                    }
                }
            }
        }
        return neighbors;
    }

    public void clear() {
        vertices.clear();
        edges.clear();
    }

    public void display() {
        System.out.println("Vertices:");
        for (Vertex vertex : vertices) {
            System.out.println("- Vertex with Value: " + vertex.getValue());
        }

        System.out.println("Edges:");
        for (Edge edge : edges) {
            System.out.println("- Edge with Label: " + edge.getLabel() + " at (" + edge.getxCoordinate() + ", " + edge.getyCoordinate() + ")");
        }
    }
}
```
- **Main class**
```java
package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
```
- **GitHub на проекта**:
```bash
git clone https://github.com/pety02/HW1_Knowledge_Based_Systems.git
```

### **Инструкции за компилиране на програмата**:
- **При клониране на проекта от GitHub**:
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
- **При наличието на source кода и конфигурационен pom.xml файл, които са в една директория (тоест не следват стандартите на Maven)**:
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
- След изпълнението на **първия тест**, посочен в main метода на Main класа, би трябвало да се изведе:
    
    Path: Arad -> Zerind -> Oradea -> Sibiu -> Rimnicu -> Lugoj -> Mehadia -> Drobeta -> Craiova -> Pitesti -> Fagaras -> Neamt -> Iasi -> Valui -> Hirsova -> Eforie -> Urziceni -> Bucharest -> Giurgiu -> Arad
    Total Distance: 30041807 km.

- След изпълнението на **втория тест**, посочен в main метода на Main класа, би трябвало да се изведе:
    
    Path: Fagaras -> Rimnicu -> Sibiu -> Lugoj -> Mehadia -> Drobeta -> Craiova -> Pitesti -> Bucharest -> Urziceni -> Hirsova -> Eforie -> Valui -> Iasi -> Neamt -> Giurgiu -> Timisoara -> Arad -> Zerind -> Fagaras
    Total Distance: 37774036 km.

- След изпълнението на **третия тест**, посочен в main метода на Main класа, би трябвало да се изведе:
    
    Path: Rimnicu -> Sibiu -> Lugoj -> Mehadia -> Drobeta -> Craiova -> Pitesti -> Fagaras -> Neamt -> Iasi -> Valui -> Hirsova -> Eforie -> Urziceni -> Bucharest -> Giurgiu -> Timisoara -> Arad -> Zerind -> Rimnicu
    Total Distance: 32713745 km.

    - **анализ на изходните данни при изпълнение на алгоритъма**:
    В обобщение на получените резултати бихме могли да кажем, че в зависимост от кой град в Румъния тръгваме различни маршрути за обход на посочената област биха били най-подходящи, тъй като евристиките биха били по-различни, тоест ще виждаме различни (оптимални според случая) маршрути. Резултатите, които Ви се визуализират доказват тези ми твърдения като Ви демонстрират обход на зададената област с един и същ алгоритъм, но преизчислени евристики (според ситуацията).
