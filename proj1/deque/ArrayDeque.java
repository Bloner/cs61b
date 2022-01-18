package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>{

    private T[] items;
    private int size;
    private int firstIndex;
    private int lastIndex;

    //Creates and empty linked list deque
    public ArrayDeque(){
        items = (T []) new Object[8];
        size = 0;
        firstIndex = 0;
        lastIndex = 1;
    }
    //System.arraycopy(items, 0, a, 0, size);
    //resize
    public void addFirst(T item){
        items[firstIndex] = item;
        firstIndex = (lastIndex == 0 ? 7 : firstIndex - 1);
        size++;
    }

    public void addLast(T item){
        items[lastIndex] = item;
        lastIndex = (lastIndex == 7 ? 0 : lastIndex + 1);
        size++;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for(int i = firstIndex; i % 8 <= lastIndex; i++){
            System.out.print(items[i % 8] + " ");
        }
        System.out.println("");
    }

    public T removeFirst(){
        if (size == 0) {
            return null;
        }
        size--;
        items[firstIndex] = null;
        firstIndex = (firstIndex == 7 ? 0 : firstIndex + 1);
        return items[firstIndex];
    }

    public T removeLast(){
        if (size == 0) {
            return null;
        }
        size--;
        items[lastIndex] = null;
        lastIndex = (lastIndex == 0 ? 7 : lastIndex - 1);
        return items[lastIndex];
    }

    public T get(int index){
        if (size <= index) {
            return null;
        }
        return items[(firstIndex + index + 1) % 8];
    }
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    public boolean equals(Object o){
        //instance of
        //System.out.println(s instanceof Simple1);//true
        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return true;
        }

        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }
}
