/*Nilsu Duran
 * vduran
 * Street Mapping
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
//graph class
public class Graph {
	
	//variables for graph class
    private int vertexCount;
    private boolean directed;
    ArrayList<Node> adj[];
    private boolean hasConnection[];
    ArrayList<Intersection> intersections;
    
    //Graph constructor
    @SuppressWarnings("unchecked")
	public Graph(int numVerticies, boolean isDirected) {
    	//add nodes which will be verticies in an adjacency list
        adj = (ArrayList<Node>[]) new ArrayList[numVerticies];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>(15);
        }
        directed = isDirected;
        hasConnection = new boolean[numVerticies];
        vertexCount = 0;
    }
    
    //getter for vertex number
    private int getVerticies() {
        return vertexCount;
    }
    
    //insert method that adds a node that connects two verticies (becomes the edge)
    public void insert(Edge e) {
        vertexCount += (hasConnection[e.v] ? 0 : (e.w != e.v) ? 1 : 0) + (hasConnection[e.w] ? 0 : 1);
        hasConnection[e.v] = true;
        hasConnection[e.w] = true;
        Node n = new Node(e.w, e.weight);
        adj[e.v].add(n);
        //if undirected, don't forget to switch endpoints so verticies point at each other
        if (!directed) {
            n = new Node(e.v, e.weight);
            adj[e.w].add(n);
        }
    }
    //Dijkstra's shortest path algorithm that uses a heap
    public static ArrayList<Integer> findShortestPath(Graph g, int a, int b) {
        boolean[] marks = new boolean[g.getVerticies()];
        double[] costs = new double[g.getVerticies()];
        int[] paths = new int[g.getVerticies()];
        Comparator<Integer> pqComp = Comparator.comparingDouble(o -> costs[o]);
        Heap heap = new Heap(g.getVerticies(), pqComp);
        for (int i = 0; i < g.getVerticies(); i++) {
            costs[i] = Double.MAX_VALUE;
            paths[i] = -1;
            marks[i] = false;
        }
        costs[a] = 0;
        heap.insert(a);
        while (!marks[b]) {
            int lowestCostIndex = heap.deleteMin();
            marks[lowestCostIndex] = true;
            for (Node n : g.adj[lowestCostIndex]) {
                double cost;
                if (costs[n.name] > (cost = (costs[lowestCostIndex] + n.val))) {
                    costs[n.name] = cost;
                    paths[n.name] = lowestCostIndex;
                    int in = heap.lookup(n.name);
                    if (in == -1) {
                        heap.insert(n.name);
                    } else {
                        heap.bubbleUp(in);
                    }
                }
            }
        }
        ArrayList<Integer> path = new ArrayList<>();
        double len = 0;
        path.add(b);
        ArrayList<Node> list;
        for (int t = paths[b]; t != -1; t = paths[t]) {
            list = g.adj[t];
            for (Node n: list){
                if (n.name == paths[t])
                    len += n.val;
            }
            path.add(t);
        }
        for (int i = path.size()-1; i >= 0 ; i--) {
            System.out.println(g.intersections.get(path.get(i)).name);
        }
        System.out.println("Shortest distance between the two points: " + distanceToDegree(len) + " miles");
        return path;
    }
    
    //forming the minimum weight spanning tree that also uses heap
    public static ArrayList<Edge> findMinimumWeightSpanningTree(Graph g) {
        ArrayList<Edge> tree = new ArrayList<>();
        boolean[] marks = new boolean[g.intersections.size()];
        double[] costs = new double[g.intersections.size()];
        int[] parents = new int[g.intersections.size()];
        Arrays.fill(marks, false);
        Arrays.fill(costs, Double.MAX_VALUE);
        Arrays.fill(parents, -1);
        costs[14] = 0;
        parents[14] = 14;
        Heap heap = new Heap(100, Comparator.comparingDouble(o -> costs[o]));
        heap.insert(14);
        Integer c;
        while ((c = heap.deleteMin())!= null){
            Edge e = new Edge(parents[c], c, 0);
            tree.add(e);
            ArrayList<Node> list = g.adj[c];
            for (Node n: list){
                if (!marks[n.name] && costs[n.name]>n.val){
                    costs[n.name] = n.val;
                    parents[n.name] = c;
                    int index = heap.lookup(n.name);
                    if (index == -1) heap.insert(n.name);
                    else heap.bubbleUp(index);
                }
            }
        }
        return tree;
    }
    
    //One degree is approx. 69.17 miles
    private static double distanceToDegree(double deg) {
        return deg * 69.17;
    }

}

//edge class
class Edge {
    int v, w;
    double weight;
    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
}