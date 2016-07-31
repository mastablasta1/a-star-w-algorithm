package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    D getHeuristicDistance(Position<P> start, Position<P> end);

    D getHeuristicDistance(GS start, GS end);

    Set<GS> getNeighborStatesOf(GS globalState);
}
