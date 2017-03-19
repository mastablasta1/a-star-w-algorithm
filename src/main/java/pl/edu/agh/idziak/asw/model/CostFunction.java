package pl.edu.agh.idziak.asw.model;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface CostFunction<CS extends CollectiveState<?>, D> {

    D getHeuristicCostEstimate(CS start, CS end);

    D getDistanceBetween(CS state, CS neighbor);

}
