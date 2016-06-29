package edu.agh.idziak.common;

import com.google.common.base.Preconditions;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class Range<T> {
    private final T lower;
    private final T higher;

    private Range(T lower, T higher) {
        this.lower = Preconditions.checkNotNull(lower);
        this.higher = Preconditions.checkNotNull(higher);
    }

    public T getLower() {
        return lower;
    }

    public T getHigher() {
        return higher;
    }

    public static <T> Range<T> of(T lower, T higher) {
        return new Range<>(lower, higher);
    }
}
