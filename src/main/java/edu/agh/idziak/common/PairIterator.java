package edu.agh.idziak.common;

import java.util.Iterator;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class PairIterator<A, B> implements Iterator<Pair<A, B>> {

    private final Iterator<A> one;
    private final Iterator<B> two;

    public PairIterator(Iterable<A> one, Iterable<B> two) {
        this.one = one.iterator();
        this.two = two.iterator();
    }

    @Override
    public boolean hasNext() {
        return one.hasNext() && two.hasNext();
    }

    @Override
    public Pair<A, B> next() {
        return new Pair<>(one.next(), two.next());
    }
}
