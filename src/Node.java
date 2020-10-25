/*Nilsu Duran
 * vduran
 * Street Mapping
 */
public class Node {
	//A node has a name and a value
    int name;
    double val;
    
    public Node(int name, double val) {
        this.name = name;
        this.val = val;
    }

    public String toString(){
        return name + " ";
    }
}
