package pl.edu.agh.idziak.asw.astar;

import com.google.common.base.MoreObjects;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class SingleValuePriorityMap<K, V extends Comparable<V>> implements DoubleValuePriorityMap<K, V> {

    private Set<K> keySet = new HashSet<>();

    private PriorityQueue<Entry<K, V>> queue = new PriorityQueue<>(Comparator.comparing(o -> o.value));

    private static class Entry<K, V extends Comparable<V>> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("key", key)
                    .add("value", value)
                    .toString();
        }

    }

    @Override
    public void add(K key, V primaryValue, V ignoredValue) {
        if (!keySet.add(key)) {
            return;
        }
        queue.add(new Entry<>(key, primaryValue));
    }

    @Override
    public boolean isEmpty() {
        return keySet.size() == 0;
    }

    @Override
    public K pollFirstKey() {
        Entry<K, V> entry = queue.poll();
        if (entry != null) {
            keySet.remove(entry.key);
            return entry.key;
        }
        return null;
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
