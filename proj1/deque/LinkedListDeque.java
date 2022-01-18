package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        Node(T i, Node m, Node n) {
            item = i;
            prev = m;
            next = n;
        }
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node curr;

        LinkedListDequeIterator() {
            curr = sentinel;
        }

        public T next() {
            curr = curr.next;
            return curr.item;
        }

        public boolean hasNext() {
            return curr.next != sentinel;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        size += 1;
        Node i = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = i;
        sentinel.next = i;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        Node i = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = i;
        sentinel.prev = i;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node curr = sentinel;
        for (int i = 0; i < size; i++) {
            System.out.print(curr.next + " ");
            curr = curr.next;
        }
        System.out.println("");
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        Node i = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return i.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        Node i = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return i.item;
    }

    @Override
    public T get(int index) {
        if (size <= index) {
            return null;
        }
        Node curr = sentinel;
        while (index-- >= 0) {
            curr = curr.next;
        }
        return curr.item;
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque || o instanceof ArrayDeque) {
            if (size == ((ArrayDeque<T>) o).size()) {
                for (int i = 0; i < size; i++) {
                    if (!get(i).equals(((ArrayDeque<T>) o).get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(index).item;
    }

    private Node getRecursiveHelper(int index) {
        if (size <= index) {
            return null;
        }
        if (index == 0) {
            return sentinel.next;
        }
        return getRecursiveHelper(index - 1).next;
    }
}
