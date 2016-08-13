package edu.agh.idziak.common;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class MapTransformer {
    public static <K, V> HashMap<K, V> hashMapFromToLists(List<K> keys, List<V> values) {
        PairIterator<K, V> it = new PairIterator<>(keys, values);
        HashMap<K, V> hashMap = new HashMap<>();
        while (it.hasNext()) {
            it.next();
            hashMap.put(it.getCurrentA(), it.getCurrentB());
        }
        return hashMap;
    }

    public static <K, V> ImmutableMap<K, V> immutableMapFromToLists(List<K> keys, List<V> values) {
        PairIterator<K, V> it = new PairIterator<>(keys, values);
        ImmutableMap.Builder<K, V> mapBuilder = ImmutableMap.builder();
        while (it.hasNext()) {
            it.next();
            mapBuilder.put(it.getCurrentA(), it.getCurrentB());
        }
        return mapBuilder.build();
    }

//    return Pair<List<K>,List<V>>
}
