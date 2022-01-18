package deque;

//add, remove, size, empty, get
public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int firstIndex;
    private int lastIndex;

    //Creates and empty linked list deque
    public ArrayDeque(){
        items = (T []) new Object[8];
        size = 0;
        firstIndex = 0;
        lastIndex = 0;
    }
    //System.arraycopy(items, 0, a, 0, size);
    //resize
    public void addFirst(T item){
        firstIndex = (lastIndex == 0 ? 7 : firstIndex - 1);
        items[firstIndex] = item;
        size++;
    }

    public void addLast(T item){
        lastIndex = (lastIndex == 7 ? 0 : lastIndex + 1);
        items[lastIndex] = item;
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
        T item = items[firstIndex];
        items[firstIndex] = null;
        firstIndex = (firstIndex == 7 ? 0 : firstIndex + 1);
        return item;
    }

    public T removeLast(){
        if (size == 0) {
            return null;
        }
        size--;
        T item = items[lastIndex];
        items[lastIndex] = null;
        lastIndex = (lastIndex == 0 ? 7 : lastIndex - 1);
        return item;
    }

    public T get(int index){
        if (size <= index) {
            return null;
        }
        return items[(firstIndex + index) % 8];
    }
    /*public Iterator<T> iterator(){

    }

    public boolean equals(Object o){
        //instance of
        System.out.println(s instanceof Simple1);//true
        return true;
    }

    private class DequeIterator implements Iterator<T> {

    }
    */
}
