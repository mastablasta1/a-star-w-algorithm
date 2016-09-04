package edu.agh.idziak.common;

import java.util.Iterator;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class PairIterator<A, B> {

    private final Iterator<A> one;
    private final Iterator<B> two;
    private A currentOne;
    private B currentTwo;

    public PairIterator(Iterable<A> one, Iterable<B> two) {
        this.one = one.iterator();
        this.two = two.iterator();
    }

    public boolean hasNext() {
        return one.hasNext() && two.hasNext();
    }

    public void next() {
        currentOne = one.next();
        currentTwo = two.next();
    }

    public A getCurrentA() {
        return currentOne;
    }

    public B getCurrentB() {
        return currentTwo;
    }
}
