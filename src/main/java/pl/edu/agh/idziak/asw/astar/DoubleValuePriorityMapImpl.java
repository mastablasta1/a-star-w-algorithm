package pl.edu.agh.idziak.asw.astar;

import com.google.common.base.MoreObjects;

import java.util.*;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class DoubleValuePriorityMapImpl<K, V extends Comparable<V>> implements DoubleValuePriorityMap<K, V> {

    private final Set<K> keySet = new HashSet<>();
    private final int secondaryValueOrder;

    public DoubleValuePriorityMapImpl(boolean preferGreaterSecondaryValues) {
        secondaryValueOrder = preferGreaterSecondaryValues ? -1 : 1;
        queue = new PriorityQueue<>(buildEntryComparator());
    }

    private Comparator<Entry<K, V>> buildEntryComparator() {
        return (o1, o2) -> {
            int primary = o1.primaryValue.compareTo(o2.primaryValue);
            return primary != 0 ? primary : secondaryValueOrder * o1.secondaryValue.compareTo(o2.secondaryValue);
        };
    }

    private PriorityQueue<Entry<K, V>> queue;

    private static class Entry<K, V extends Comparable<V>> {
        private final K key;
        private final V primaryValue;
        private final V secondaryValue;

        public Entry(K key, V primaryValue, V secondaryValue) {
            this.key = key;
            this.primaryValue = primaryValue;
            this.secondaryValue = secondaryValue;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("key", key)
                    .add("primaryValue", primaryValue)
                    .add("secondaryValue", secondaryValue)
                    .toString();
        }

    }

    @Override
    public void add(K key, V primaryValue, V secondaryValue) {
        if (!keySet.add(key)) {
            return;
        }
        queue.add(new Entry<>(key, primaryValue, secondaryValue));
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
