package edu.agh.idziak.common;

import java.util.Iterator;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class WalkingPairIterator<T> implements Iterator<SingleTypePair<T>> {

    private final Iterator<T> iterator;
    private T previous;

    public WalkingPairIterator(Iterable<T> iterable) {
        iterator = iterable.iterator();
        if (iterator.hasNext()) {
            previous = iterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public SingleTypePair<T> next() {
        T first = previous;
        T second = iterator.next();
        previous = second;
        return Pair.ofSingleType(first, second);
    }
}
