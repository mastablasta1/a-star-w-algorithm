package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<U extends Comparable<U>> {

    Set<GlobalState<U>> getNeighborStatesOf(GlobalState<U> globalState);

    Set<EntityState<U>> getNeighborStatesOf(EntityState<U> entityState);

    Set<EntityState<U>> getNeighborStatesOf(Iterable<EntityState<U>> entityStates);

    U getHeuristicDistance(Position<U> start, Position<U> end);

    U getHeuristicDistance(GlobalState<U> start, GlobalState<U> end);

}
