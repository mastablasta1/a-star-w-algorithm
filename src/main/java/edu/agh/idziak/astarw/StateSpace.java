package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<S, P, D extends Comparable<D>> {
    Set<S> getStates();

    Set<S> getStatesAccessibleFrom(S state);

    D getEstimatedDistanceBetween(P pos1, P pos2);
}
