package edu.agh.idziak.common;

import java.util.*;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class ValueSortedHashMapNOTGOOD<K, V extends Comparable<V>> implements Map<K, V> {

    private final Comparator<Pair<K, V>> comparator = (o1, o2) -> {
        if (o1.getOne() == o2.getOne())
            return 0;
        return o1.getTwo().compareTo(o2.getTwo());
    };
    private final TreeMap<Pair<K, V>, V> map = new TreeMap<>(comparator);

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
