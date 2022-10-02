import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class GQTest {
    @Test
    // tests the constructor by checking head value, length, tail value and that tail and head point to same node
    // tests on String Queue and Integer Queue
    void testConstructor() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);

        assertEquals(GQ.getHead().data, "A", "Head data values did not match in Constructor");
        assertEquals(GQ.getLength(), 1, "Length value did not match in Constructor");
        assertEquals(GQ.tail.data, "A", "Tail value did not match in Constructor");
        assertEquals(GQ.tail, GQ.getHead(), "Head and tail did not match in Constructor");

        assertEquals(GQInt.getHead().data, 1, "Head data values did not match in Constructor");
        assertEquals(GQInt.getLength(), 1, "Length value did not match in Constructor");
        assertEquals(GQInt.tail.data, 1, "Tail value did not match in Constructor");
        assertEquals(GQInt.tail, GQInt.getHead(), "Head and tail did not match in Constructor");
    }

    @Test
    // tests the Node constructor by checking head value, the next node is null, tail value and that the next tail value is null
    // tests on String Queue and Integer Queue
    void testNodeConstructor() {
        GenericQueue<String> GQ = new GenericQueue<String>("B");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(2);

        assertEquals(GQ.getHead().data, "B", "Head value did not match in node Constructor");
        assertEquals(GQ.getHead().next, null, "Head next value did not match in node Constructor");
        assertEquals(GQ.tail.data, "B", "Tail value did not match in node Constructor");
        assertEquals(GQ.tail.next, null, "Tail next value did not match in node Constructor");

        assertEquals(GQInt.getHead().data, 2, "Head value did not match in node Constructor");
        assertEquals(GQInt.getHead().next, null, "Head next value did not match in node Constructor");
        assertEquals(GQInt.tail.data, 2, "Tail value did not match in node Constructor");
        assertEquals(GQInt.tail.next, null, "Tail next value did not match in node Constructor");
    }

    @Test
    // tests the removeTail method by checking the removed tail value, the length, the head value and the tail value
    // tests on String Queue and Integer Queue
    void testRemoveTail() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);

        assertEquals(GQ.removeTail(), "A", "Tail did not remove correct value");
        assertEquals(GQ.getLength(), 0, "Length does not match");
        assertEquals(GQ.getHead(), null, "Head does not match");
        assertEquals(GQ.tail, null, "Tail did not move");

        assertEquals(GQInt.removeTail(), 4, "Tail did not remove correct value");
        assertEquals(GQInt.getLength(), 3, "Length does not match");
        assertEquals(GQInt.getHead().data, 1, "Head does not match");
        assertEquals(GQInt.tail.data, 3, "Tail did not move");
    }

    @Test
    // tests the enqueue method by checking length, head data value, tail data value and elements in list
    // tests on String Queue and Integer Queue
    void testEnqueue() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GQ.enqueue("B");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        ArrayList<Integer> GQIntsList = new ArrayList<>();
        GQIntsList.add(1);
        GQIntsList.add(2);
        GQIntsList.add(3);
        GQIntsList.add(4);

        assertEquals(GQ.getLength(), 2, "Length does not match");
        assertEquals(GQ.getHead().data, "A", "Head does not match");
        assertEquals(GQ.tail.data, "B", "Tail does not match");

        assertEquals(GQInt.getLength(), 4, "Length does not match");
        assertEquals(GQInt.getHead().data, 1, "Head does not match");
        assertEquals(GQInt.tail.data, 4, "Tail does not match");
        assertEquals(GQInt.getHead().next.data, 2, "Head next value does not match. Did not insert correctly in list");

        assertArrayEquals(GQInt.dumpList().toArray(), GQIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Queue and Integer Queue
    // test the dequeue method by looking at length, head value, tail value and elements
    void testDequeue() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GQ.enqueue("B");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        ArrayList<Integer> GQIntsList = new ArrayList<>();
        GQIntsList.add(2);
        GQIntsList.add(3);
        GQIntsList.add(4);

        assertEquals(GQ.dequeue(), "A", "Did not dequeue correct element");
        assertEquals(GQ.getLength(), 1, "Length does not match");
        assertEquals(GQ.getHead().data, "B", "Head does not match");
        assertEquals(GQ.tail.data, "B", "Tail does not match");
        assertEquals(GQ.dequeue(), "B", "Did not dequeue correct element");
        assertEquals(GQ.dequeue(), null, "Dequeue was not null");

        assertEquals(GQInt.dequeue(), 1, "Did not dequeue correct element");
        assertEquals(GQInt.getLength(), 3, "Length does not match");
        assertEquals(GQInt.getHead().data, 2, "Head does not match");
        assertEquals(GQInt.tail.data, 4, "Tail does not match");

        assertArrayEquals(GQInt.dumpList().toArray(), GQIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests iterator methods hasNext and next by using a foreach loop to check elements and using a while loop
    void testIterator() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        int num = 1;

        assertEquals(GQ.iterator().hasNext(), true, "Returned false instead of true");
        assertEquals(GQ.iterator().next(), "A", "Returned wrong value");

        for (Integer GQIntIter: GQInt) {
            assertEquals(GQIntIter, num, "Returned wrong value");
            num++;
        }

        num = 1;
        Iterator<Integer> iterator = GQInt.iterator();
        while (iterator.hasNext()) {
            assertEquals(iterator.next(), num, "Wrong value");
            num++;
        }
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests the reverseIterator class methods of hasNext and next by using a while loop to check elements
    void testReverseIterator() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        int num = 4;

        assertEquals(GQ.descendingIterator().hasNext(), true, "Returned false instead of true");
        assertEquals(GQ.descendingIterator().next(), "A", "Returned wrong value");

        Iterator<Integer> descendingIterator = GQInt.descendingIterator();
        while (descendingIterator.hasNext()) {
            assertEquals(descendingIterator.next(), num, "Wrong value");
            num--;
        }
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests listIterator class methods hasNext, next, hasPrevious, previous, nextIndex and previousIndex
    // does this by checking with multiple loops. hasNext, next, nextIndex were grouped together and hasPrevious, previous and previousIndex were together
    void testListIterator() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        int num = 3;

        ListIterator<String> listIteratorS = GQ.listIterator(0);
        while (listIteratorS.hasNext()) {
            assertEquals(listIteratorS.next(), "A", "Wrong value");
        }
        ListIterator<String> listIteratorSP = GQ.listIterator(0);
        while (listIteratorSP.hasPrevious()) {
            assertEquals(listIteratorSP.previous(), "A", "Wrong value");
        }

        ListIterator<Integer> listIterator = GQInt.listIterator(2);
        int index = 2;
        assertEquals(listIterator.hasNext(), true, "Was supposed to have more elements");
        while (listIterator.hasNext()) {
            assertEquals(listIterator.nextIndex(), index + 1, "Incorrect next index");
            assertEquals(listIterator.next(), num, "Wrong value");
            index++;
            num++;
        }

        num = 3;
        ListIterator<Integer> listIteratorP = GQInt.listIterator(2);
        index = 2;
        assertEquals(listIteratorP.hasPrevious(), true, "Was supposed to have more elements");
        while (listIteratorP.hasPrevious()) {
            assertEquals(listIteratorP.previousIndex(), index - 1, "Incorrect next index");
            assertEquals(listIteratorP.previous(), num, "Wrong value");
            index--;
            num--;
        }
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests setting and getting length with manual input
    void testSetAndGetLength() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQ.setLength(1);
        GQInt.setLength(10);

        assertEquals(GQ.getLength(), 1, "Length was not set right");
        assertEquals(GQInt.getLength(), 10, "Length was not set right");
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests getting and setting head with manual change
    void testSetAndGetHead() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GQ.enqueue("B");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GenericList<String>.Node<String> node1 = GQ.getHead().next;
        GQ.setHead(node1);
        GenericList<Integer>.Node<Integer> node2 = GQInt.getHead().next;
        GQInt.setHead(node2);

        assertEquals(GQ.getHead(), node1, "Length was not set right");
        assertEquals(GQInt.getHead(), node2, "Length was not set right");
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests delete method by checking length, head value, tail value and elements
    void testDelete() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GQ.enqueue("B");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        ArrayList<Integer> GQIntsList = new ArrayList<>();
        GQIntsList.add(2);
        GQIntsList.add(3);
        GQIntsList.add(4);

        assertEquals(GQ.delete(), "A", "Did not dequeue correct element");
        assertEquals(GQ.getLength(), 1, "Length does not match");
        assertEquals(GQ.getHead().data, "B", "Head does not match");
        assertEquals(GQ.tail.data, "B", "Tail does not match");
        assertEquals(GQ.delete(), "B", "Did not dequeue correct element");
        assertEquals(GQ.delete(), null, "Dequeue was not null");

        assertEquals(GQInt.delete(), 1, "Did not dequeue correct element");
        assertEquals(GQInt.getLength(), 3, "Length does not match");
        assertEquals(GQInt.getHead().data, 2, "Head does not match");
        assertEquals(GQInt.tail.data, 4, "Tail does not match");

        assertArrayEquals(GQInt.dumpList().toArray(), GQIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests dumpList method by comparing manual arraylist to returned array list. checks length too
    void testDumpList() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GQ.dequeue();
        ArrayList<String> GQStringsList = new ArrayList<>();

        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);
        ArrayList<Integer> GQIntsList = new ArrayList<>();
        GQIntsList.add(1);
        GQIntsList.add(2);
        GQIntsList.add(3);
        GQIntsList.add(4);

        assertEquals(GQ.getLength(), GQStringsList.size(), "Not same length");
        assertArrayEquals(GQ.dumpList().toArray(), GQStringsList.toArray(), "Elements do not match");
        assertEquals(GQInt.getLength(), GQIntsList.size(), "Not same length");
        assertArrayEquals(GQInt.dumpList().toArray(), GQIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Queue and Integer Queue
    // tests set and get methods where index value is changed. changed index values to test
    void testSetAndGet() {
        GenericQueue<String> GQ = new GenericQueue<String>("A");
        GQ.enqueue("B");
        GenericQueue<Integer> GQInt = new GenericQueue<Integer>(1);
        GQInt.enqueue(2);
        GQInt.enqueue(3);
        GQInt.enqueue(4);

        GQ.set(0, "Z");
        GQInt.set(2,7);

        assertEquals(GQ.get(0), "Z", "Element did not match");
        assertEquals(GQInt.get(2), 7, "Element did not match");
    }
}