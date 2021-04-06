import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Elvira Häggström
 * Last edit: 2021-04-04
 */

public class PriorityQueue<Item extends Comparable<Item>> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    @Override
    public Iterator iterator() {
        return new PriorityQueueIterator();
    }

    private static class Node<Item extends Comparable> implements Comparable<Node<Item>> {
        private Item item;
        private Node<Item> next;


        @Override
        public int compareTo(Node o) {
            return this.item.compareTo(o.item);
        }
    }

    public PriorityQueue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Item first() {
        return first.item;
    }

    public int size() {
        return size;
    }

    public void insert(Item item) {
        boolean inserted = false;
        Node<Item> newNode = new Node<Item>();
        newNode.item = item;

        Node<Item> current = first;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
            inserted = true;
        } else if (first.item.compareTo(item) < 0) {
            // Insert before first
            newNode.next = first;
            Node<Item> temp = first;
            first = newNode;
            if (this.size() == 1) {
                last = temp;
            }
            inserted = true;
        } else {
            while (current.next != null && !inserted) {
                if (current.next.item.compareTo(item) < 0) {
                    // Insert before current.next, i.e. at current's position
                    Node<Item> temp = current.next;
                    current.next = newNode;
                    newNode.next = temp;
                    inserted = true;
                } else {
                    current = current.next;
                }
            }
            if (!inserted) {
                last.next = newNode;
                last = newNode;
                inserted = true;
            }
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Cannot dequeue from empty Queue");

        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) last = null;
        return item;
    }

    public void delete(Item removeMe) {
        Node<Item> current = first;

        if (first.item.equals(removeMe)) {
            if (size() != 1) {
                first.next = first;
            } else {
                first = null;
                last = null;
            }
        }

        while (current.next != null) {
            if (current.next.item.equals(removeMe)) {
                if (current.next == last) {
                    current.next = null;
                    last = current;
                } else {
                    Node<Item> tmp = current.next.next;
                    current.next.next = null;
                    current.next = tmp;
                }
            }
            current = current.next;
        }
        size--;
    }

    public String toString() {
        if (isEmpty()) {
            return "Empty";
        }
        StringBuilder sb = new StringBuilder();
        Node<Item> current = first;
        while (current != null) {
            sb.append(current.item).append("\n");
            current = current.next;
        }
        return sb.toString();
    }

    private class PriorityQueueIterator implements Iterator<Item> {
        Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
