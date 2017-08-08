package pl.edu.agh.idziak.asw.astar;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class DoubleValuePriorityMapImpl<K, V extends Comparable<V>> implements DoubleValuePriorityMap<K, V> {

    private final Set<K> keySet = new HashSet<>();
    private final int secondaryValueOrder;
    private PriorityQueue<EntryImpl<K, V>> queue;

    public DoubleValuePriorityMapImpl(boolean preferGreaterSecondaryValues) {
        secondaryValueOrder = preferGreaterSecondaryValues ? -1 : 1;
        queue = new PriorityQueue<>(buildEntryComparator());
    }

    private Comparator<EntryImpl<K, V>> buildEntryComparator() {
        return (o1, o2) -> {
            int primary = o1.primaryValue.compareTo(o2.primaryValue);
            return primary != 0 ? primary : secondaryValueOrder * o1.secondaryValue.compareTo(o2.secondaryValue);
        };
    }

    private static class EntryImpl<K, V extends Comparable<V>> implements Entry<K, V> {
        private final K key;
        private final V primaryValue;
        private final V secondaryValue;

        public EntryImpl(K key, V primaryValue, V secondaryValue) {
            this.key = key;
            this.primaryValue = primaryValue;
            this.secondaryValue = secondaryValue;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getPrimaryValue() {
            return primaryValue;
        }

        @Override
        public V getSecondaryValue() {
            return secondaryValue;
        }

        @Override
        public String toString() {
            return "["
                    + "key=" + key
                    + ", primary=" + primaryValue
                    + ", secondary=" + secondaryValue
                    + "]";
        }

    }

    @Override
    public void add(K key, V primaryValue, V secondaryValue) {
        if (!keySet.add(key)) {
            return;
        }
        queue.add(new EntryImpl<>(key, primaryValue, secondaryValue));
    }

    @Override
    public boolean isEmpty() {
        return keySet.size() == 0;
    }

    @Override
    public Entry<K, V> pollFirstKey() {
        Entry<K, V> entry = queue.poll();
        if (entry != null) {
            keySet.remove(entry.getKey());
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
