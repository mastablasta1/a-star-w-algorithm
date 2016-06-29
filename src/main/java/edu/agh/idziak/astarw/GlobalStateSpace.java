package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface GlobalStateSpace<U extends Comparable<U>> {

    Set<GlobalState<U>> getNeighborStatesOf(GlobalState<U> globalState);

    Set<EntityState<U>> getNeighborStatesOf(EntityState<U> entityState);

    int getStateSize();

    U getHeuristicDistance(Position<U> start, Position<U> end);
}
