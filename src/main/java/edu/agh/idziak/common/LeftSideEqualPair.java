package edu.agh.idziak.common;

/**
 * Created by Tomasz on 13.07.2016.
 */
public class LeftSideEqualPair<X, Y> extends Pair<X, Y> {

    public LeftSideEqualPair(X one, Y two) {
        super(one, two);
    }

    @Override
    public int hashCode() {
        return getOne().hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return getOne().equals(obj);
    }
}
