package pl.edu.agh.idziak.asw.model;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface DistanceHeuristic<CS extends CollectiveState<?>, D> {

    D estimateHeuristicDistance(CS start, CS end);

    D getDistanceBetween(CS state, CS neighbor);

}
