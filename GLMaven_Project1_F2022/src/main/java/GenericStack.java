public class GenericStack<T> extends GenericList<T>{
    Node<T> tail;

    // parameter constructor
    public GenericStack(T value) {
        Node<T> head = new Node<T>(value);
        head.data = value;
        head.next = null;
        setHead(head);
        setLength(getLength() + 1);
        tail = getHead();
    }

    // retrieves and removes the tail of the list
    public T removeTail() {
        Node<T> tempHead = getHead();
        Node<T> prev = getHead();
        T tailValue;

        while (tempHead.next != null) {
            prev = tempHead;
            tempHead = tempHead.next;
        }
        tailValue = tail.data;
        tail = prev;
        setLength(getLength() - 1);
        if (getLength() == 0) {
            setHead(null);
            tail = null;
        }
        return tailValue;
    }

    // adds new nodes to the front of the list
    @Override
    public void add(T data) {
        Node<T> addNode = new Node<T>(data);
        addNode.next = getHead();
        setHead(addNode);
        setLength(getLength() + 1);
    }

    // adds value to stack
    public void push(T data) {
        add(data);
    }

    // removes top stack value
    public T pop() {
        return delete();
    }
}
