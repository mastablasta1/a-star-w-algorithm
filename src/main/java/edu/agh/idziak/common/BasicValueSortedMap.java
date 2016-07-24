package edu.agh.idziak.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class BasicValueSortedMap<K, V extends Comparable<V>> implements ValueSortedMap<K, V> {

    private TreeMap<V, LinkedList<K>> valueToKeysMap = Maps.newTreeMap(Comparable::compareTo);
    private Set<K> keySet = new HashSet<>();

    @Override
    public void put(K key, V value) {
        LinkedList<K> keySetForValue = valueToKeysMap.get(value);
        if (keySetForValue == null) {
            valueToKeysMap.put(value, keySetForValue = Lists.newLinkedList());
        }
        keySetForValue.add(0, key);
        keySet.add(key);
    }

    @Override
    public boolean isEmpty() {
        return keySet.isEmpty();
    }

    @Override
    public Pair<K, V> pollEntryWithLowestValue() {
        Map.Entry<V, LinkedList<K>> entry = valueToKeysMap.firstEntry();
        LinkedList<K> keySetForValue = entry.getValue();
        K key = keySetForValue.pollFirst();
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
