package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>{

    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node m, Node n) {
            item = i;
            prev = m;
            next = n;
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

    public void addFirst(T item) {
        size += 1;
        Node i = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = i;
        sentinel.next = i;
    }

    public void addLast(T item) {
        size += 1;
        Node i = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = i;
        sentinel.prev = i;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque() {

    }

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

    public T get(int index) {
        if (size <= index) {
            return null;
        }
        Node curr = sentinel;
        while (index >= 0) {
            curr = curr.next;
            index--;
        }
        return curr.item;
    }
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public boolean equals(Object o) {
            //instance of
        //System.out.println(s instanceof Simple1);//true
        return true;
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

    public class LinkedListDequeIterator implements Iterator<T> {
        public LinkedListDequeIterator() {

        }
        public T next(){
            return sentinel.item;
        }

        public boolean hasNext(){
            return true;
        }
    }
}
