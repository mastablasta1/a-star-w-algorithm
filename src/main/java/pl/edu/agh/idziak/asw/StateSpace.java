package pl.edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P
        extends Comparable<P>> {

    D getHeuristicCost(ES start, ES end);

    D getHeuristicCost(CS start, CS end);

    Set<CS> getNeighborStatesOf(CS globalState);

    Set<ES> getNeighborStatesOf(EntityState<P> globalState);

}
