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

    public static <T> SingleTypePair<T> ofSameType(T one, T two) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!one.equals(pair.one)) return false;
        return two.equals(pair.two);
    }

    @Override
    public int hashCode() {
        int result = one.hashCode();
        result = 31 * result + two.hashCode();
        return result;
    }
}
