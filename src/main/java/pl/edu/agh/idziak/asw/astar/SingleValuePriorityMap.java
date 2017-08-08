package pl.edu.agh.idziak.asw.astar;

import com.google.common.base.MoreObjects;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

public class SingleValuePriorityMap<K, V extends Comparable<V>> implements DoubleValuePriorityMap<K, V> {

    private Set<K> keySet = new HashSet<>();

    private PriorityQueue<EntryImpl<K, V>> queue = new PriorityQueue<>(Comparator.comparing(o -> o.value));

    private static class EntryImpl<K, V extends Comparable<V>> implements Entry<K, V> {
        private K key;
        private V value;

        public EntryImpl(K key, V value) {
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

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getPrimaryValue() {
            return value;
        }

        @Override
        public V getSecondaryValue() {
            return null;
        }
    }

    @Override
    public void add(K key, V primaryValue, V ignoredValue) {
        if (!keySet.add(key)) {
            return;
        }
        queue.add(new EntryImpl<>(key, primaryValue));
    }

    @Override
    public boolean isEmpty() {
        return keySet.size() == 0;
    }

    @Override
    public Entry<K, V> pollFirstKey() {
        EntryImpl<K, V> entry = queue.poll();
        if (entry != null) {
            keySet.remove(entry.key);
            return entry;
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

    @Override
    public Stream<? extends Entry<K, V>> stream() {
        return queue.stream();
    }

    @Override
    public Stream<? extends Entry<K, V>> parallelStream() {
        return queue.parallelStream();
    }
}
