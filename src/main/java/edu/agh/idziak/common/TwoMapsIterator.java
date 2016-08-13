package edu.agh.idziak.common;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tomasz on 07.08.2016.
 */
public class TwoMapsIterator<K, V> {

    private final Iterator<Map.Entry<K, V>> it1;
    private Map<K, V> map2;
    private Map.Entry<K, V> current1;
    private V value2;

    public TwoMapsIterator(Map<K, V> map1, Map<K, V> map2) {
        it1 = map1.entrySet().iterator();
        this.map2 = map2;
    }

    public void next() {
        current1 = it1.next();
        value2 = map2.get(current1.getKey());
    }

    public K getKey() {
        return current1.getKey();
    }

    public V getFirstValue() {
        return current1.getValue();
    }

    public V getSecondValue() {
        return value2;
    }

    public boolean hasNext() {
        return it1.hasNext();
    }
}
