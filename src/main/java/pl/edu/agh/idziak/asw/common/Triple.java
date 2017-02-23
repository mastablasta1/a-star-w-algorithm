package pl.edu.agh.idziak.asw.common;

import com.google.common.base.Preconditions;

/**
 * Created by Tomasz on 07.08.2016.
 */
public class Triple<X, Y, Z> {

    private final X one;
    private final Y two;
    private final Z three;

    Triple(X one, Y two, Z three) {
        this.one = Preconditions.checkNotNull(one);
        this.two = Preconditions.checkNotNull(two);
        this.three = Preconditions.checkNotNull(three);
    }


    public static <X, Y, Z> Triple<X, Y, Z> of(X one, Y two, Z three) {
        return new Triple<>(one, two, three);
    }

    public Y getTwo() {
        return two;
    }

    public X getOne() {
        return one;
    }

    public Z getThree() {
        return three;
    }

    @Override
    public String toString() {
        return "[" + one + ", " + two + ',' + three + ']';
    }
}
