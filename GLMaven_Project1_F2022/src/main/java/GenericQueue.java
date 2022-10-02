public class GenericQueue<T> extends GenericList<T>{
    Node<T> tail;

    // constructor that initializes the linked list head with value passed in
    // sets the head and tail data members
    public GenericQueue(T value) {
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

    // adds node to the back of the list
    @Override
    public void add(T data) {
        Node<T> addNode = new Node<T>(data);
        tail.next = addNode;
        tail = addNode;
        tail.next = null;
        setLength(getLength() + 1);
    }

    // adds value to the back
    public void enqueue(T data) {
        add(data);
    }

    // deletes first value
    public T dequeue() {
        return delete();
    }
}
