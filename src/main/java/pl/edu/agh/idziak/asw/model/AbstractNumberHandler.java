package pl.edu.agh.idziak.asw.model;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface AbstractNumberHandler<D extends Comparable<D>> {
    D add(D a, D b);

    boolean greaterOrEqual(D a, D b);

    D getZero();

}
