package pl.edu.agh.idziak.asw.astar;

import java.util.stream.Stream;

public interface DoubleValuePriorityMap<K, V extends Comparable<V>> {
    void add(K key, V primaryValue, V secondaryValue);

    boolean isEmpty();

    Entry<K, V> pollFirstKey();

    boolean containsKey(K key);

    int size();

    Stream<? extends Entry<K, V>> stream();

    Stream<? extends Entry<K, V>> parallelStream();

    interface Entry<K, V extends Comparable<V>> {
        K getKey();

        V getPrimaryValue();

        V getSecondaryValue();
    }
}
