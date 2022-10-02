import java.util.ListIterator;

public class GLListIterator<E> implements ListIterator<E> {
    private int index;
    GenericList<E> theList;

    // parameter constructor
    public GLListIterator(GenericList<E> list, int theIndex) {
        theList = list;
        index = theIndex;
    }

    // checks if there is another element after current element and returns true if there is and false if there is not
    public boolean hasNext() {
        if (index >= theList.getLength()) {
            return false;
        } else {
            return true;
        }
    }

    // returns the element and iterates it
    public E next() {
        return theList.get(index++);
    }

    // checks if there is a previous element and if there is then returns true, otherwise returns false
    public boolean hasPrevious() {
        if (index < 0) {
            return false;
        } else {
            return true;
        }
    }

    // returns the current element and decreases index
    public E previous() {
        return theList.get(index--);
    }

    // returns next element index
    public int nextIndex() {
        return (1 + index);
    }

    // returns previous element index
    public int previousIndex() {
        return (index - 1);
    }

    // next three were not required
    public void remove() {

    }

    public void set(E e) {

    }

    public void add(E e) {

    }
}
