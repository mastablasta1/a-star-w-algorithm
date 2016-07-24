package edu.agh.idziak.common;

public interface ValueSortedMap<K, V extends Comparable<V>> {
    void put(K key, V value);

    boolean isEmpty();

    Pair<K, V> pollEntryWithLowestValue();

    boolean containsKey(K key);

    int size();
}
