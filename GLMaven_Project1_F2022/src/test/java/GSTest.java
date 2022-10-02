import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class GSTest {
    @Test
    // tests the constructor by checking head value, length, tail value and that tail and head point to same node
    // tests on String stack and Integer stack
    void testConstructor() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);

        assertEquals(GS.getHead().data, "A", "Head data values did not match in Constructor");
        assertEquals(GS.getLength(), 1, "Length value did not match in Constructor");
        assertEquals(GS.tail.data, "A", "Tail value did not match in Constructor");
        assertEquals(GS.tail, GS.getHead(), "Head and tail did not match in Constructor");

        assertEquals(GSInt.getHead().data, 1, "Head data values did not match in Constructor");
        assertEquals(GSInt.getLength(), 1, "Length value did not match in Constructor");
        assertEquals(GSInt.tail.data, 1, "Tail value did not match in Constructor");
        assertEquals(GSInt.tail, GSInt.getHead(), "Head and tail did not match in Constructor");
    }

    @Test
    // tests the Node constructor by checking head value, the next node is null, tail value and that the next tail value is null
    // tests on String Stack and Integer Stack
    void testNodeConstructor() {
        GenericStack<String> GS = new GenericStack<String>("B");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(2);

        assertEquals(GS.getHead().data, "B", "Head value did not match in node Constructor");
        assertEquals(GS.getHead().next, null, "Head next value did not match in node Constructor");
        assertEquals(GS.tail.data, "B", "Tail value did not match in node Constructor");
        assertEquals(GS.tail.next, null, "Tail next value did not match in node Constructor");

        assertEquals(GSInt.getHead().data, 2, "Head value did not match in node Constructor");
        assertEquals(GSInt.getHead().next, null, "Head next value did not match in node Constructor");
        assertEquals(GSInt.tail.data, 2, "Tail value did not match in node Constructor");
        assertEquals(GSInt.tail.next, null, "Tail next value did not match in node Constructor");
    }

    @Test
    // tests the removeTail method by checking the removed tail value, the length, the head value and the tail value
    // tests on String Stack and Integer Stack
    void testRemoveTail() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);

        assertEquals(GS.removeTail(), "A", "Tail did not remove correct value");
        assertEquals(GS.getLength(), 0, "Length does match");
        assertEquals(GS.getHead(), null, "Head does not match");
        assertEquals(GS.tail, null, "Tail did not move");

        assertEquals(GSInt.removeTail(), 1, "Tail did not remove correct value");
        assertEquals(GSInt.getLength(), 3, "Length does match");
        assertEquals(GSInt.getHead().data, 4, "Head does not match");
        assertEquals(GSInt.tail.data, 2, "Tail did not move");
    }

    @Test
    // tests the push method by checking length, head data value, tail data value and elements in list
    // tests on String Stack and Integer Stack
    void testPush() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GS.push("B");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);
        ArrayList<Integer> GSIntsList = new ArrayList<>();
        GSIntsList.add(4);
        GSIntsList.add(3);
        GSIntsList.add(2);
        GSIntsList.add(1);

        assertEquals(GS.getLength(), 2, "Length does not match");
        assertEquals(GS.getHead().data, "B", "Head does not match");
        assertEquals(GS.tail.data, "A", "Tail does not match");

        assertEquals(GSInt.getLength(), 4, "Length does not match");
        assertEquals(GSInt.getHead().data, 4, "Head does not match");
        assertEquals(GSInt.tail.data, 1, "Tail does not match");
        assertEquals(GSInt.getHead().next.data, 3, "Head next value does not match. Did not insert correctly in list");

        assertArrayEquals(GSInt.dumpList().toArray(), GSIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Stack and Integer Stack'
    // tests the pop method by looking at length, head value, tail value and elements
    void testPop() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GS.push("B");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);

        assertEquals(GS.pop(), "B", "Did not pop correct element");
        assertEquals(GS.getLength(), 1, "Length does not match");
        assertEquals(GS.getHead().data, "A", "Head does not match");
        assertEquals(GS.tail.data, "A", "Tail does not match");
        assertEquals(GS.pop(), "A", "Did not pop correct element");
        assertEquals(GS.pop(), null, "Pop was not null");

        assertEquals(GSInt.pop(), 4, "Did not pop correct element");
        assertEquals(GSInt.getLength(), 3, "Length does not match");
        assertEquals(GSInt.getHead().data, 3, "Head does not match");
        assertEquals(GSInt.tail.data, 1, "Tail does not match");

        ArrayList<Integer> GSIntsList = new ArrayList<>();
        GSIntsList.add(3);
        GSIntsList.add(2);
        GSIntsList.add(1);

        assertArrayEquals(GSInt.dumpList().toArray(), GSIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests iterator methods hasNext and next by using a foreach loop to check elements and using a while loop
    void testIterator() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);
        int num = 4;

        assertEquals(GS.iterator().hasNext(), true, "Returned false instead of true");
        assertEquals(GS.iterator().next(), "A", "Returned wrong value");

        for (Integer GSIntIter: GSInt) {
            assertEquals(GSIntIter, num, "Returned wrong value");
            num--;
        }

        num = 4;
        Iterator<Integer> iterator = GSInt.iterator();
        while (iterator.hasNext()) {
            assertEquals(iterator.next(), num, "Wrong value");
            num--;
        }
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests the reverseIterator class methods of hasNext and next by using a while loop to check elements
    void testReverseIterator() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);
        int num = 1;

        assertEquals(GS.descendingIterator().hasNext(), true, "Returned false instead of true");
        assertEquals(GS.descendingIterator().next(), "A", "Returned wrong value");

        Iterator<Integer> descendingIterator = GSInt.descendingIterator();
        while (descendingIterator.hasNext()) {
            assertEquals(descendingIterator.next(), num, "Wrong value");
            num++;
        }
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests listIterator class methods hasNext, next, hasPrevious, previous, nextIndex and previousIndex
    // does this by checking with multiple loops. hasNext, next, nextIndex were grouped together and hasPrevious, previous and previousIndex were together
    void testListIterator() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);
        int num = 2;

        ListIterator<String> listIteratorS = GS.listIterator(0);
        while (listIteratorS.hasNext()) {
            assertEquals(listIteratorS.next(), "A", "Wrong value");
        }

        ListIterator<Integer> listIterator = GSInt.listIterator(2);
        int index = 2;
        assertEquals(listIterator.hasNext(), true, "Was supposed to have more elements");
        while (listIterator.hasNext()) {
            assertEquals(listIterator.nextIndex(), index + 1, "Incorrect next index");
            assertEquals(listIterator.next(), num, "Wrong value");
            index++;
            num--;
        }

        num = 2;
        ListIterator<Integer> listIteratorP = GSInt.listIterator(2);
        index = 2;
        assertEquals(listIteratorP.hasPrevious(), true, "Was supposed to have more elements");
        while (listIteratorP.hasPrevious()) {
            assertEquals(listIteratorP.previousIndex(), index - 1, "Incorrect next index");
            assertEquals(listIteratorP.previous(), num, "Wrong value");
            index--;
            num++;
        }
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests setting and getting length with manual input
    void testSetAndGetLength() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GS.setLength(1);
        GSInt.setLength(10);

        assertEquals(GS.getLength(), 1, "Length was not set right");
        assertEquals(GSInt.getLength(), 10, "Length was not set right");
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests getting and setting head with manual change
    void testSetAndGetHead() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GS.push("B");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GenericList<String>.Node<String> node1 = GS.getHead().next;
        GS.setHead(node1);
        GenericList<Integer>.Node<Integer> node2 = GSInt.getHead().next;
        GSInt.setHead(node2);

        assertEquals(GS.getHead(), node1, "Length was not set right");
        assertEquals(GSInt.getHead(), node2, "Length was not set right");
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests delete method by checking length, head value, tail value and elements
    void testDelete() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GS.push("B");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);

        assertEquals(GS.delete(), "B", "Did not pop correct element");
        assertEquals(GS.getLength(), 1, "Length does not match");
        assertEquals(GS.getHead().data, "A", "Head does not match");
        assertEquals(GS.tail.data, "A", "Tail does not match");
        assertEquals(GS.delete(), "A", "Did not pop correct element");
        assertEquals(GS.delete(), null, "Pop was not null");

        assertEquals(GSInt.delete(), 4, "Did not pop correct element");
        assertEquals(GSInt.getLength(), 3, "Length does not match");
        assertEquals(GSInt.getHead().data, 3, "Head does not match");
        assertEquals(GSInt.tail.data, 1, "Tail does not match");

        ArrayList<Integer> GSIntsList = new ArrayList<>();
        GSIntsList.add(3);
        GSIntsList.add(2);
        GSIntsList.add(1);

        assertArrayEquals(GSInt.dumpList().toArray(), GSIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests dumpList method by comparing manual arraylist to returned array list. checks length too
    void testDumpList() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GS.pop();
        ArrayList<String> GQStringsList = new ArrayList<>();

        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);
        ArrayList<Integer> GSIntsList = new ArrayList<>();
        GSIntsList.add(4);
        GSIntsList.add(3);
        GSIntsList.add(2);
        GSIntsList.add(1);

        assertEquals(GS.getLength(), GQStringsList.size(), "Not same length");
        assertArrayEquals(GS.dumpList().toArray(), GQStringsList.toArray(), "Elements do not match");
        assertEquals(GSInt.getLength(), GSIntsList.size(), "Not same length");
        assertArrayEquals(GSInt.dumpList().toArray(), GSIntsList.toArray(), "Elements do not match");
    }

    @Test
    // tests on String Stack and Integer Stack
    // tests set and get methods where index value is changed. changed index values to test
    void testSetAndGet() {
        GenericStack<String> GS = new GenericStack<String>("A");
        GS.push("B");
        GenericStack<Integer> GSInt = new GenericStack<Integer>(1);
        GSInt.push(2);
        GSInt.push(3);
        GSInt.push(4);

        GS.set(0, "SZK");
        GSInt.set(2,11);

        assertEquals(GS.get(0), "SZK", "Element did not match");
        assertEquals(GSInt.get(2), 11, "Element did not match");
    }
}