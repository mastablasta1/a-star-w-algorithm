package edu.agh.idziak.asw;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Planner<SS extends StateSpace<GS, ES, D, P>, GS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    OutputPlan<SS, GS, ES, P, D> calculatePlan(InputPlan<SS, GS, ES, P, D> inputPlan);
}