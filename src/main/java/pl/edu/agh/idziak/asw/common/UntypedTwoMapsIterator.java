package pl.edu.agh.idziak.asw.common;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tomasz on 07.08.2016.
 */
public class UntypedTwoMapsIterator<V> {

    private final Iterator<? extends Map.Entry<?, V>> it1;
    private Map<?, V> map2;
    private Map.Entry<?, V> current1;
    private V value2;

    public UntypedTwoMapsIterator(Map<?, V> map1, Map<?, V> map2) {
        it1 = map1.entrySet().iterator();
        this.map2 = map2;
    }

    public void next() {
        current1 = it1.next();
        value2 = map2.get(current1.getKey());
    }

    public Object getKey() {
        return current1.getKey();
    }

    public V getFirstValue() {
        return current1.getValue();
    }

    public V getSecondValue() {
        return value2;
    }

    public boolean hasNext() {
        return it1.hasNext();
    }
}
