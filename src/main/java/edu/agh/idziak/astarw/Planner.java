package edu.agh.idziak.astarw;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Planner<E, P, S, D extends Comparable<D>> {

    Plan<E, P, S, D> calculatePlan(PlannerInput<E, P, S, D> plannerInput);
}
