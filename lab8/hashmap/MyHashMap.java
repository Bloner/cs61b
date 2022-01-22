package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Leon
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private final int INITIAL_SIZE = 16;
    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private double loadFactor = 0.75;
    private int items;

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[INITIAL_SIZE];
        for (int i = 0; i < INITIAL_SIZE; i++) {
            buckets[i] = createBucket();
        }
        items = 0;
        size = INITIAL_SIZE;
    }

    public MyHashMap(int initialSize) {
        size = initialSize;
        buckets = new Collection[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = createBucket();
        }
        items = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = initialSize;
        buckets = new Collection[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = createBucket();
        }
        loadFactor = maxLoad;
        items = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return null;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }

    // Your code won't compile until you do so!

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            buckets[i] = createBucket();
        }
        items = 0;
    }

    @Override
    public boolean containsKey(K key) {
        Node nd = getNode(key);
        return nd != null;
    }

    @Override
    public V get(K key) {
        Node nd = getNode(key);
        return nd == null ? null : nd.value;
    }

    private Node getNode(K key) {
        Collection<Node> c = buckets[calHash(key.hashCode())];
        for (Node n: c) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return items;
    }

    @Override
    public void put(K key, V value) {
        if (items > size * loadFactor) {
            buckets = resize(size * 2);
        }
        int index = calHash(key.hashCode());
        int flag = 0;
        for (Node n: buckets[index]) {
            if (n.key.equals(key)) {
                n.value = value;
                flag = 1;
            }
        }
        if (flag == 0) {
            items++;
            buckets[index].add(new Node(key, value));
        }
    }

    private Collection<Node>[] resize(int capability) {

        Collection[] temp = new Collection[capability];
        for (int i = 0; i < capability; i++) {
            temp[i] = createBucket();
        }
        for (int i = 0; i < size; i++) {
            for (Node n: buckets[i]) {
                temp[calHash(n.key.hashCode())].add(new Node(n.key, n.value));
            }
        }
        size = capability;
        return temp;
    }

    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        for (int i = 0; i < size; i++) {
            for (Node n: buckets[i]) {
                res.add(n.key);
            }
        }
        return res;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("none");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("none");
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHSIterator();
    }

    private int calHash(int h) {
        return h < 0 ? Math.floorMod(h, size) : h % size;
    }

    private class MyHSIterator implements Iterator<K>{
        List<K> l;
        int curr;
        int lsize;
        MyHSIterator() {
            lsize = 0;
            l = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (Node n: buckets[i]) {
                    l.add(n.key);
                    lsize++;
                }
            }
            curr = -1;
        }
        public boolean hasNext() {
            return curr + 1 < lsize;
        }
        public K next() {
            curr++;
            return l.get(curr);
        }
    }
}
