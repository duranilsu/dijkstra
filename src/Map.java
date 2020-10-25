/*Nilsu Duran
 * vduran
 * Street Mapping
 */
import java.util.ArrayList;
//map class
public class Map {
	
	//map class that takes a graph and arraylists of intersections and roads
    Graph g;
    private ArrayList<Intersection> intersections = new ArrayList<>();
    private ArrayList<Edge> roads = new ArrayList<>();

    //intersection insert method
    public void insertIntersection(Intersection a) {
        intersections.add(a);
    }
    //between two intersections, a new road is created (helper method)
    private void insertRoad(Intersection a, Intersection b) {
        roads.add(new Road(a, b));
    }
    //actual insertion of roads to the map
    public void insertRoad(int v, int w) {
        insertRoad(intersections.get(v), intersections.get(w));
    }
    //creating graph and adding roads and intersections onto it
    public void createGraph() {
        g = new Graph(intersections.size(), false);
        for (Edge e : roads) {
            g.insert(e);
        }
        g.intersections = intersections;
    }
    //using the algorithm on the graph
    public ArrayList<Integer> findShortestPath(int a, int b) {
        if (g == null)
            createGraph();
        return Graph.findShortestPath(g, a, b);
    }
    //getter for roads
    public ArrayList<Edge> getRoads() {
        return roads;
    }
    //getter for intersections
    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }
    //a road object created with two intersections, which is essentially a weighted edge
   public class Road extends Edge {
        Road(Intersection v, Intersection w) {
            super(v.id, w.id, Math.sqrt(Math.pow(v.x - w.x, 2) + Math.pow(v.y - w.y, 2)));
        }
    }
}
//intersection class
class Intersection {
    double x;
    double y;
    String name;
    int id;

    public Intersection(double x, double y, String name, int id) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.id = id;
    }
}
