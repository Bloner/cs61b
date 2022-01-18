package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private class ArrayDequeIterator implements Iterator<T> {
        private int count;

        ArrayDequeIterator() {
            count = 0;
        }

        public boolean hasNext() {
            return count < size;
        }

        public T next() {
            T returnItem = items[(firstIndex + 1 + count) % items.length];
            count += 1;
            return returnItem;
        }
    }

    private T[] items;
    private int size;
    private int firstIndex;
    private int lastIndex;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        firstIndex = 0;
        lastIndex = 1;
    }

    private T[] resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (firstIndex < lastIndex) {
            System.arraycopy(items, firstIndex + 1, a, 1, size);
        } else {
            System.arraycopy(items, (firstIndex + 1) % items.length, a, 1,
                    items.length - 1 - firstIndex);
            System.arraycopy(items, 0, a, items.length - firstIndex, lastIndex);
        }
        firstIndex = 0;
        lastIndex = size + 1;
        return a;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length - 1) {
            items = resize(size * 2);
        }
        items[firstIndex] = item;
        firstIndex = (firstIndex == 0 ? items.length - 1 : firstIndex - 1);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length - 1) {
            items = resize(size * 2);
        }
        items[lastIndex] = item;
        lastIndex = (lastIndex == items.length - 1 ? 0 : lastIndex + 1);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int count = 0;
        for (int i = firstIndex + 1; count < size; i++, count++){
            System.out.print(items[i % items.length] + " ");
        }
        System.out.println("");
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (items.length >= 16 && size <= items.length / 4) {
            items = resize(items.length / 2);
        }
        size--;
        firstIndex = (firstIndex == items.length - 1 ? 0 : firstIndex + 1);
        return items[firstIndex];
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (items.length >= 16 && size <= items.length / 4) {
            items = resize(items.length / 2);
        }
        size--;
        lastIndex = (lastIndex == 0 ? items.length - 1 : lastIndex - 1);
        return items[lastIndex];
    }

    @Override
    public T get(int index) {
        if (size <= index) {
            return null;
        }
        return items[(firstIndex + index + 1) % items.length];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof ArrayDeque) {
            int size1 = size;
            int size2 = ((ArrayDeque<T>) o).size();
            if (size1 == size2) {
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
}
