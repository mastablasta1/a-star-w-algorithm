package edu.agh.idziak.common;

import com.google.common.base.Preconditions;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class Range<T extends Comparable<T>> {
    private final T lower;
    private final T higher;

    private Range(T one, T two) {
        Preconditions.checkNotNull(one);
        Preconditions.checkNotNull(two);
        if (one.compareTo(two) < 0) {
            lower = one;
            higher = two;
        } else {
            lower = two;
            higher = one;
        }
    }

    public T getLower() {
        return lower;
    }

    public T getHigher() {
        return higher;
    }

    public static <T extends Comparable<T>> Range<T> of(T one, T two) {
        return new Range<>(one, two);
    }
}
