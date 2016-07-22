package edu.agh.idziak.common;

import java.util.Iterator;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class DoubleIterator<E> implements Iterator<SingleTypePair<E>> {

    private final Iterator<E> one;
    private final Iterator<E> two;

    public DoubleIterator(Iterable<E> one, Iterable<E> two) {
        this.one = one.iterator();
        this.two = two.iterator();
    }

    @Override
    public boolean hasNext() {
        return one.hasNext() && two.hasNext();
    }

    @Override
    public SingleTypePair<E> next() {
        return new SingleTypePair<>(one.next(), two.next());
    }
}
