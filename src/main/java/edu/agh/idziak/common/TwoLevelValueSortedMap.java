package edu.agh.idziak.common;

import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class TwoLevelValueSortedMap<K, V extends Comparable<V>, C extends Comparator<K>> implements ValueSortedMap<K, V> {

    private final Comparator<K> keyComparator;
    private TreeMap<V, PriorityQueue<K>> valueToKeysMap = Maps.newTreeMap(Comparable::compareTo);
    private Set<K> keySet = new HashSet<>();

    public TwoLevelValueSortedMap(Comparator<K> comparator) {
        this.keyComparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        PriorityQueue<K> keySetForValue = valueToKeysMap.get(value);
        if (keySetForValue == null) {
            valueToKeysMap.put(value, keySetForValue = new PriorityQueue<>(keyComparator));
        }
        keySetForValue.add(key);
        keySet.add(key);
    }

    @Override
    public boolean isEmpty() {
        return keySet.isEmpty();
    }

    @Override
    public Pair<K, V> pollEntryWithLowestValue() {
        Map.Entry<V, PriorityQueue<K>> entry = valueToKeysMap.firstEntry();
        PriorityQueue<K> keySetForValue = entry.getValue();
        K key = keySetForValue.remove();
        if (keySetForValue.isEmpty()) {
            valueToKeysMap.remove(entry.getKey());
        }
        keySet.remove(key);
        return Pair.of(key, entry.getKey());
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    @Override
    public int size() {
        return keySet.size();
    }
}
