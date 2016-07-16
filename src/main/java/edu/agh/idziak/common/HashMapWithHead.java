package edu.agh.idziak.common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class HashMapWithHead<K, V extends Comparable<V>> extends HashMap<K, V> {

    private final Comparator<V> comparator;
    private K headKey;
    private V headValue;

    public HashMapWithHead(Comparator<V> comparator) {
        this.comparator = comparator;
    }

    public HashMapWithHead() {
        comparator = Comparable::compareTo;
    }

    @Override
    public V put(K key, V value) {
        checkHead(key, value);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        super.putAll(m);
        forEach(this::checkHead);
    }

    public V getHeadValue() {
        return headValue;
    }

    public K getHeadKey() {
        return headKey;
    }

    private void checkHead(K key, V value) {
        if (comparator.compare(value, headValue) < 0) {
            headKey = key;
            headValue = value;
        }
    }
}
