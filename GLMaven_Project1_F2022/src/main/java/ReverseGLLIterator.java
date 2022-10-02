import java.util.Iterator;

public class ReverseGLLIterator<I> implements Iterator<I> {

    GenericList<I> theList;
    private int index;

    // parameter constructor
    public ReverseGLLIterator(GenericList<I> list) {
        theList = list;
        index = theList.getLength() - 1;
    }

    // checks to see if there is another value in the data structure
    // starts from tail
    public boolean hasNext() {
        if (index < 0) {
            return false;
        } else {
            return true;
        }
    }

    // returns the current value in the data structure
    public I next() {
        return theList.get(index--);
    }
}
