import java.util.Iterator;

public class GLLIterator<I> implements Iterator<I> {
    private int index;
    GenericList<I> theList;

    // parameter constructor
    public GLLIterator(GenericList<I> list) {
        theList = list;
        index = 0;
    }

    // checks to see if there is another value in the data structure
    public boolean hasNext() {
        if (index >= theList.getLength()) {
            return false;
        } else {
            return true;
        }
    }

    // returns the current value in the data structure
    public I next() {
       return theList.get(index++);
    }
}
