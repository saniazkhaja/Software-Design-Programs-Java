import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
public abstract class GenericList<T> implements Iterable<T> {
    // head of list
    private Node<T> head;
    // length of list
    private int length;

    // prints the items in the list
    public void print() {
        if (length == 0) {
            System.out.println("Empty List");
        } else {
            Node<T> tempHead = head;
            while (tempHead != null) {
                System.out.println(tempHead.data);
                tempHead = tempHead.next;
            }
        }
    }

    // adds the value to the list
    public abstract void add(T data);

    // returns the first value of the list and deletes the node
    // returns null if the list is empty
    public T delete() {
        if (length == 0) {
            return null;
        } else {
            T firstNodeValue = head.data;
            head = head.next;
            length--;
            return firstNodeValue;
        }
    }

    // stores all values in the list into ArrayList and returns it
    // list would be empty at the end
    public ArrayList<T> dumpList() {
        ArrayList<T> nodesArrayList = new ArrayList<T>();
        while (head != null) {
            nodesArrayList.add(head.data);
            head = head.next;
        }
        length = 0;
        return nodesArrayList;
    }

    // returns the value at a specific index
    // returns null if the index is out of bounds
    public T get(int index) {
        Node<T> tempHead = head;
        if (index >= length || index < 0) {
            return null;
        } else {
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    return tempHead.data;
                } else {
                    tempHead = tempHead.next;
                }
            }
        }
        return tempHead.data;
    }

    // replaces the index element in the list with the parameter element value
    // returns previous list element
    // if index is out of bounds then returns null
    public T set(int index, T element) {
        Node<T> originalHead = head;
        T prevElement = head.data;

        if (index >= length || index < 0) {
            return null;
        } else {
            for (int i = 0; i <= index; i++) {
                // checking if index is reached and then storing old value and changes index to new value
                if (i == index) {
                    prevElement = head.data;
                    head.data = element;
                } else {
                    head = head.next;
                }
            }
        }
        head = originalHead;
        return prevElement;
    }

    // gets length of list
    public int getLength() {
        return length;
    }

    // sets length of list
    public void setLength(int theLength) {
        this.length = theLength;
    }

    // gets head of List
    public Node getHead() {
        return head;
    }

    // sets head of list
    public void setHead(Node<T> theHead) {
        this.head = theHead;
    }


    // returns an iterator over the elements of the list
    public Iterator <T> iterator(){
        return new GLLIterator<T>(this);
    }

    // returns an iterator over the elements of the list in reverse order (tail from head)
    public Iterator<T> descendingIterator() {
        return new ReverseGLLIterator<T>(this);
    }

    // returns a list-iterator of the elements of this list starting at the parameter index
    public ListIterator<T> listIterator(int index) {
        return new GLListIterator<T>(this, index);
    }

    // used to create nodes in linked list class
    class Node<T> {
        T data;
        Node<T> next;

        // parameter constructor

        public Node(T val) {
            data = val;
        }
    }
}


