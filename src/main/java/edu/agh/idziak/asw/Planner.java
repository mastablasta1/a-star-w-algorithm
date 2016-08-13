package edu.agh.idziak.asw;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Planner<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    OutputPlan<SS, GS, P, D> calculatePlan(InputPlan<SS, GS, P, D> inputPlan);
}