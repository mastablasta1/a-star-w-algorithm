package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<GS extends GlobalState<U>, U extends Comparable<U>> {

    Set<GS> getNeighborStatesOf(GS globalState);

    U getHeuristicDistance(GS start, GS end);

}
