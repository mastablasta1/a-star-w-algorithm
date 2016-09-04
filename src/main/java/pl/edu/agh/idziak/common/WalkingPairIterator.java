package pl.edu.agh.idziak.common;

import java.util.Iterator;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class WalkingPairIterator<T> {

    private final Iterator<T> iterator;
    private T first;
    private T second;

    public WalkingPairIterator(Iterable<T> iterable) {
        iterator = iterable.iterator();
        if (iterator.hasNext()) {
            second = iterator.next();
        }
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public void next() {
        T next = iterator.next();
        first = second;
        second = next;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
