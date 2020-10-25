/*Nilsu Duran
 * vduran
 * Street Mapping
 */
import java.util.Comparator;
//heap class used for shortest path algorithm
public class Heap {
	
	//heap variables plus comparator for nodes
    private int size;
    private int maxSize;
    private Integer[] arr;
    private Comparator<Integer> nodeComparator;
    
    //class constructor
    public Heap(int initialSize, Comparator<Integer> nodeComparator) {
        maxSize = initialSize;
        size = 1;
        arr = new Integer[initialSize];
        this.nodeComparator = nodeComparator;
    }
    
    //getter for heap size
    public int getSize() {
        return size;
    }
    
    //heap insert method with resizing
    public void insert(Integer item) {
        if (size >= maxSize)
            resizeArray(size * 2);
        arr[size] = item;
        bubbleUp(size);
        size++;
    }
    
    //helper method for resize
    private void resizeArray(int newSize) {
        Integer[] temp = new Integer[newSize];
        System.arraycopy(arr, 0, temp, 0, size);
        arr = temp;
    }
    
    //bubble up for heap
    public void bubbleUp(int index) {
        if (index == 1)
            return;
        if (nodeComparator.compare(arr[index / 2],(arr[index])) == 1) {
            swap(index / 2, index);
            bubbleUp(index / 2);
        }
    }
    
    //delete min for heap
    public Integer deleteMin(){
        if (size == 1)
            return null;
        size--;
        swap(1, size);
        bubbleDown(1);
        return arr[size];
    }
    
    //bubble down for heap
    private void bubbleDown(int index) {
        if (index * 2 >= size)
            return;
        if (arr[index]==null) {
            if (index * 2 + 1 >= size) {
                swap(index * 2, index);
                return;
            }
            if (nodeComparator.compare(arr[index * 2],(arr[index * 2 + 1]))== -1) {
                swap(index, index * 2);
                bubbleDown(index * 2);
            } else if (nodeComparator.compare(arr[index*2+1],(arr[index]))==-1) {
                swap(index, index * 2 + 1);
                bubbleDown(index * 2 + 1);
            }
        } else {
            if (index * 2 + 1 >= size && nodeComparator.compare(arr[index*2],(arr[index]))==-1) {
                swap(index * 2, index);
                return;
            }
            if (nodeComparator.compare(arr[index * 2],(arr[index * 2 + 1])) == -1 && nodeComparator.compare(arr[index*2],(arr[index]))==-1) {
                swap(index, index * 2);
                bubbleDown(index * 2);
            } else if (nodeComparator.compare(arr[index*2+1],(arr[index]))==-1){
                swap(index, index * 2 + 1);
                bubbleDown(index * 2 + 1);
            }
        }
    }
    
    //lookup for heap
   public int lookup(int id){
        for (int i = 1; i < size; i++){
            if (arr[i] == id) {
                return i;
            }
        }
        return -1;
    }
   
   //swap method for heap
    private void swap(int indA, int indB) {
        Integer temp = arr[indA];
        arr[indA] = arr[indB];
        arr[indB] = temp;
    }

}
