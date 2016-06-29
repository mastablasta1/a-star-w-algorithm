package edu.agh.idziak.common;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Pair<T> {
    private final T one;
    private final T two;

    public Pair(T one, T two) {
        this.one = one;
        this.two = two;
    }

    public static <T> Pair<T> of(T one, T two) {
        return new Pair<T>(one, two);
    }

    public T getTwo() {
        return two;
    }

    public T getOne() {
        return one;
    }
}
