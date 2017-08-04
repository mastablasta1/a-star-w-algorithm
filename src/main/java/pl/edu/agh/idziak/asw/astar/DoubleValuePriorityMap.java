package pl.edu.agh.idziak.asw.astar;

public interface DoubleValuePriorityMap<K, V extends Comparable<V>> {
    void add(K key, V primaryValue, V secondaryValue);

    boolean isEmpty();

    K pollFirstKey();

    boolean containsKey(K key);

    int size();
}
