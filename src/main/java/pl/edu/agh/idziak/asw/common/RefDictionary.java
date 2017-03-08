package pl.edu.agh.idziak.asw.common;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tomasz on 07.03.2017.
 */
public class RefDictionary<K, V extends RefDictionary.IterableAcceptor<K>> {

    private Node<K, V> root = new Node<>(null, null);

    public RefDictionary() {
        root.children = new HashMap<>();
    }

    public void put(Iterable<K> keyIterable, V value) {
        Iterator<K> iterator = keyIterable.iterator();
        put(iterator, value);
    }

    public void put(Iterator<K> iterator, V value) {
        Node<K, V> current = root;
        while (iterator.hasNext()) {
            K key = iterator.next();
            Node<K, V> child = current.children.get(key);
            if (child == null) {
                child = new Node<>(key, current);
                current.children.put(key, child);
            }
            current = child;
        }
        final Node<K, V> leafNode = current;
        leafNode.value = value;
        leafNode.value.acceptIterableKey(buildIterableKey(leafNode));
    }

    private Iterable<K> buildIterableKey(final Node<K, V> leafNode) {
        return () -> new Iterator<K>() {
            private Node<K, V> current = leafNode;

            @Override public boolean hasNext() {
                return current.key != null;
            }

            @Override public K next() {
                K currentKey = current.key;
                current = current.parent;
                return currentKey;
            }
        };
    }

    public V get(ImmutableList<K> keyIterable) {
        Iterator<K> iterator = keyIterable.iterator();
        return get(iterator);
    }

    public V get(Iterator<K> iterator) {
        Node<K, V> current = root;
        while (iterator.hasNext()) {
            K key = iterator.next();
            current = current.children.get(key);
            if (current == null)
                return null;
        }
        return current.value;
    }


    private static class Node<K, V> {

        private K key;
        private Node<K, V> parent;
        private V value;
        Map<K, Node<K, V>> children = new HashMap<>();

        private Node(K key, Node<K, V> parent) {
            this.key = key;
            this.parent = parent;
        }
    }

    public interface IterableAcceptor<T> {

        void acceptIterableKey(Iterable<T> iterable);
    }
}
