package edu.agh.idziak.astarw;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface AbstractNumberHandler<U extends Comparable<U>> {
    U add(U one, U two);

    boolean lessThan(U one, U two);

    boolean greaterOrEqual(U one, U two);

    U getZero();
}
