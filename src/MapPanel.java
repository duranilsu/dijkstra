/*Nilsu Duran
 * vduran
 * Street Mapping
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
//class where GUI is implemented
public class MapPanel extends JPanel {
	
    //panel variables
	private static final long serialVersionUID = 1L;
	private Edge[] roads;
    private ArrayList<Point> intersections;
    ArrayList<Integer> path;
    ArrayList<Edge> tree;
    private Dimension dimdim;
    int a = 0, b = 0;
    
    //panel constructor that takes in the map I created
    public MapPanel(Map m) {
        double scale;
        dimdim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setPreferredSize(dimdim);
        ArrayList<Intersection> ints = m.getIntersections();
        intersections = new ArrayList<>();
        roads = new Edge[m.getRoads().size()];
        roads = m.getRoads().toArray(roads);

        double minX = ints.get(0).x;
        double maxX = minX;
        double minY = ints.get(0).y;
        double maxY = minY;
        
        for(Intersection in: ints){
            if (in.x > maxX)
                maxX = in.x;
            if (in.x < minX)
                minX = in.x;
            if (in.y > maxY)
                maxY = in.y;
            if (in.y < minY)
                minY = in.y;
        }
        
        double diffX = maxX - minX;
        double diffY = maxY - minY;
        double scaleX = diffX/dimdim.getWidth();
        double scaleY = diffY/dimdim.getHeight();
        scale = Double.max(scaleX, scaleY)*1.5;
        minX -= diffX*.2;
        minY -= diffY*.2;
        for (Intersection anInt : ints) {
            intersections.add(new Point((int) ((anInt.x - minX) / scale), (int)((anInt.y - minY) / scale)));
        }
    }
    //paint method that draws roads and colors
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, dimdim.width, dimdim.height);
        g.setColor(Color.black);
        for(Edge e: roads){
            g.drawLine(intersections.get(e.v).x, intersections.get(e.v).y, intersections.get(e.w).x, intersections.get(e.w).y);
        }
  
        g.setColor(Color.green);
        if (tree != null){
            for (Edge e: tree){
                g.drawLine(intersections.get(e.v).x, intersections.get(e.v).y, intersections.get(e.w).x, intersections.get(e.w).y);
            }
        }
        g.setColor(Color.red);
        if (path != null){
            for (int i = 0; i < path.size()-1; i++) {
                g.drawLine(intersections.get(path.get(i)).x, intersections.get(path.get(i)).y, intersections.get(path.get(i+1)).x, intersections.get(path.get(i+1)).y);

            }
        }
    }
    //finding closest node
    public int findClosestNode(int x, int y){
        for (int i = 0; i < intersections.size(); i++) {
            if (Math.abs(intersections.get(i).x - x) + Math.abs(intersections.get(i).y-y)<2)
                return i;
        }
        return -1;
    }
}
