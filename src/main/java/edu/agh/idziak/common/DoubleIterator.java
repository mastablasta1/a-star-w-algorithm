package edu.agh.idziak.common;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class DoubleIterator<E> implements Iterator<Pair<E>> {

    private final Iterator<E> one;
    private final Iterator<E> two;

    public DoubleIterator(Collection<E> one, Collection<E> two) {
        this.one = one.iterator();
        this.two = two.iterator();
    }

    @Override
    public boolean hasNext() {
        return one.hasNext() && two.hasNext();
    }

    @Override
    public Pair<E> next() {
        return Pair.of(one.next(), two.next());
    }
}
