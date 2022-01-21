package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        private K key;
        private V val;
        private BSTNode left, right;
        private int size;

        BSTNode(K k, V v, int s) {
            key = k;
            val = v;
            size = s;
        }
    }

    private BSTNode root;

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(root, key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        BSTNode lookup = get(root, key);
        if (lookup != null) {
            return lookup.val;
        }
        return null;
    }

    private BSTNode get(BSTNode n, K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (n == null) {
            return null;
        }
        int cmp = n.key.compareTo(key);
        if(cmp == 0) {
            return n;
        } else if(cmp > 0) {
            return get(n.left, key);
        } else {
            return get(n.right, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    private int size(BSTNode n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call put() with a null key");
        }
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode n, K key, V value) {
        if (n == null) {
            return new BSTNode(key, value, 1);
        }
        int cmp = n.key.compareTo(key);
        if (cmp == 0) {
            n.val = value;
        }
        else if (cmp > 0) {
            n.left = put(n.left, key, value);
        }
        else {
            n.right = put(n.right, key, value);
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Don't support");
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Don't support");
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Don't support");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Don't support");
    }

    //print BSTMap in order of increasing Key
    public void printInOrder() {

    }

    private class BSTMapIter implements Iterator<K> {

        /** Create a new ULLMapIter by setting cur to the first node in the
         *  linked list that stores the key-value pairs. */
        public BSTMapIter() {
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public K next() {
            throw new UnsupportedOperationException("Don't support");
        }
    }
}
