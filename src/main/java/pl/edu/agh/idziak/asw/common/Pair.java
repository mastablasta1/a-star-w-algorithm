package pl.edu.agh.idziak.asw.common;

import com.google.common.base.Preconditions;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class Pair<X, Y> {

    private final X one;
    private final Y two;

    Pair(X one, Y two) {
        this.one = Preconditions.checkNotNull(one);
        this.two = Preconditions.checkNotNull(two);
    }

    public static <X, Y> Pair<X, Y> of(X one, Y two) {
        return new Pair<>(one, two);
    }

    public static <T> SingleTypePair<T> ofSingleType(T one, T two) {
        return new SingleTypePair<>(one, two);
    }

    public Y getTwo() {
        return two;
    }

    public X getOne() {
        return one;
    }

    @Override
    public String toString() {
        return "[" + one + ", " + two + ']';
    }
}
