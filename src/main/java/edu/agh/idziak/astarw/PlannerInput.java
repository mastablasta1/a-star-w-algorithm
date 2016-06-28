package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface PlannerInput<E, P, S, D extends Comparable<D>> {

    Set<EntityBasicPlan<E, P>> getEntityBasicPlans();
    StateSpace<S, P, D> getStateSpace();
}
