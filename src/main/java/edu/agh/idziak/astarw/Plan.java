package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Plan<E, P, S, D extends Comparable<D>> {
    Set<EntityFullPlan<E, P>> getEntityFullPlans();

    StateSpace<S, P, D> getStateSpace();

    Set<DeviationZone<D>> getDeviationZones();
}
