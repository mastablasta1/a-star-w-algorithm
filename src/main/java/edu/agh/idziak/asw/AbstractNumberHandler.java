package edu.agh.idziak.asw;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface AbstractNumberHandler<D extends Comparable<D>> {
    D add(D one, D two);

    boolean greaterOrEqual(D one, D two);

    boolean lessThan(D one, D two);

    D getZero();

    D getOne();
}
