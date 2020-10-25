/*Nilsu Duran
 * vduran
 * Street Mapping
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

//test class
public class test {
	
	//file reader takes in the txt file and creates a JPanel with a map on it
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("monroe.txt"));
        Map map = new Map();
        HashMap<String, Integer> nameToMap = new HashMap<>(100);
        while (initArrays(map, nameToMap, bufferedReader));
        MapPanel mapPanel = new MapPanel(map);
        JFrame frame = new JFrame("Street Mapper");
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.add(mapPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        
        //points are chosen by clicks
        mapPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                int x = p.x;
                int y = p.y;
                int node = mapPanel.findClosestNode(x, y);
                if (node == -1){
                    return;
                }
                if (mapPanel.a == 0){
                    mapPanel.a = node;
                } else {
                    mapPanel.b = node;
                    mapPanel.path = map.findShortestPath(mapPanel.a, mapPanel.b);
                    mapPanel.a = 0;
                    mapPanel.b = 0;
                }
                mapPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        
        map.createGraph();
        mapPanel.tree = Graph.findMinimumWeightSpanningTree(map.g);
        frame.setVisible(true);

    }
    //reading line and creating intersections and roads
    private static boolean initArrays(Map m, HashMap<String, Integer> nameToNumMap, BufferedReader bufferedReader) throws IOException {
        String str = bufferedReader.readLine();
        if (str == null)
            return false;
        String[] split = str.split("\t");
        if (split[0].equals("i")) {
            int t;
            Intersection intersection = new Intersection(Double.parseDouble(split[3]), -Double.parseDouble(split[2]), split[1], (t = m.getIntersections().size()));
            m.insertIntersection(intersection);
            nameToNumMap.put(split[1], t);
        } else {
            int v = nameToNumMap.get(split[2]);
            int w = nameToNumMap.get(split[3]);
            m.insertRoad(v, w);
        }
        return true;
    }
}
