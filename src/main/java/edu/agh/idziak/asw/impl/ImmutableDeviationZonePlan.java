package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Tomasz on 13.08.2016.
 */
class ImmutableDeviationZonePlan<CS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> implements DeviationZonePlan<P> {

    private Map<Object, Map<EntityState<P>, D>> plansForEntities;
    private DeviationZone<P> deviationZone;
    private StateSpace<CS, P, D> stateSpace;


    ImmutableDeviationZonePlan(Map<Object, Map<EntityState<P>, D>> plansForEntities, DeviationZone<P> deviationZone, StateSpace<CS, P, D> stateSpace) {
        this.plansForEntities = plansForEntities;
        this.deviationZone = deviationZone;
        this.stateSpace = stateSpace;
    }

    @Override
    public DeviationZone<P> getDeviationZone() {
        return deviationZone;
    }

    @Override
    public EntityState<P> getBestMoveFrom(EntityState<P> entityState, Object entity) {
        Map<EntityState<P>, D> entityPlan = plansForEntities.get(entity);
        if (entityPlan == null) {
            throw new NoSuchElementException("Deviation zone does not involve given entity.");
        }
        if (deviationZone.getStates().contains(entityState)) {
            throw new NoSuchElementException("Deviation zone does not contain given state.");
        }
        Set<EntityState<P>> neighbors = stateSpace.getNeighborStatesOf(entityState);
        Optional<EntityState<P>> min = neighbors.stream().min((o1, o2) -> entityPlan.get(o1).compareTo(entityPlan.get(o2)));
        return min.orElse(null);
    }

}
