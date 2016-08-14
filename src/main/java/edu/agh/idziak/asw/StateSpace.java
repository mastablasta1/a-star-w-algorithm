package edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<CS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    D getHeuristicDistance(EntityState<P> start, EntityState<P> end);

    D getHeuristicDistance(CS start, CS end);

    Set<CS> getNeighborStatesOf(CS globalState);

    Set<? extends EntityState<P>> getNeighborStatesOf(EntityState<P> globalState);

}
