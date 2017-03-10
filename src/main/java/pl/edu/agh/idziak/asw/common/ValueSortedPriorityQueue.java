package pl.edu.agh.idziak.asw.common;

import com.google.common.base.MoreObjects;

import java.util.*;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class ValueSortedPriorityQueue<K, V extends Comparable<V>> {

    private long counter = 0;
    private K firstKey;

    public K getFirstKey() {
        return firstKey;
    }

    private static class Entry<K, V extends Comparable<V>> {

        private K key;
        private V value;
        private long stampId;

        public Entry(K key, V value, long stampId) {
            this.key = key;
            this.value = value;
            this.stampId = stampId;
        }

        @Override public String toString() {
            return MoreObjects.toStringHelper(this)
                              .add("key", key)
                              .add("value", value)
                              .toString();
        }
    }

    private PriorityQueue<Entry<K, V>> queue = new PriorityQueue<>((o1, o2) -> {
        int valueComp = o1.value.compareTo(o2.value);
        if (valueComp == 0) {
            return o1.stampId > o2.stampId ? -1 : 1;
        }
        return valueComp;
    });
    private Set<K> keySet = new HashSet<>();


    public void add(K key, V value) {
        if (!keySet.add(key)) {
            return;
        }
        queue.add(new Entry<>(key, value, counter++));
    }

    public boolean isEmpty() {
        return keySet.size() == 0;
    }

    public void pollFirst() {
        Entry<K, V> entry = queue.poll();
        if (entry != null) {
            firstKey = entry.key;
            keySet.remove(entry.key);
        } else {
            firstKey = null;
        }
    }

    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    public int size() {
        return keySet.size();
    }
}
