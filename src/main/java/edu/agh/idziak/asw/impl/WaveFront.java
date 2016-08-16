package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class WaveFront<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> {

    private AbstractNumberHandler<D> abstractNumberHandler;

    public WaveFront(AbstractNumberHandler<D> abstractNumberHandler) {
        this.abstractNumberHandler = abstractNumberHandler;

    }

    public List<DeviationZonePlan<P>> makeDeviationZonePlans(PlanningData<SS, CS, ES, P, D> planningData) {
        Set<DeviationZone<P>> deviationZones = planningData.getDeviationZones();

        return deviationZones.stream()
                .map((deviationZone) -> buildPlanForDevZone(deviationZone, planningData))
                .collect(toList());
    }

    private DeviationZonePlan<P> buildPlanForDevZone(DeviationZone<P> deviationZone, PlanningData<SS, CS, ES, P, D> planningData) {

        Set<?> involvedEntities = deviationZone.getInvolvedEntities();

        Map<Object, Map<EntityState<P>, D>> plansForEntities = new HashMap<>();

        for (Object entity : involvedEntities) {
            EntityState<P> targetState = deviationZone.targetState(entity);
            Map<EntityState<P>, D> entityPlan = buildDistanceMapForState(targetState, deviationZone, planningData);
            plansForEntities.put(entity, entityPlan);
        }

        return new ImmutableDeviationZonePlan<>(plansForEntities, deviationZone, planningData.getInputPlan().getStateSpace());
    }

    private Map<EntityState<P>, D> buildDistanceMapForState(EntityState<P> targetState, DeviationZone<P> deviationZone, PlanningData<SS, CS, ES, P, D> planningData) {
        SS stateSpace = planningData.getInputPlan().getStateSpace();
        Set<EntityState<P>> devZoneStates = deviationZone.getStates();

        Map<EntityState<P>, D> distance = new HashMap<>();
        Queue<EntityState<P>> queue = new LinkedList<>();

        distance.put(targetState, abstractNumberHandler.getZero());
        queue.add(targetState);

        while (!queue.isEmpty()) {
            EntityState<P> current = queue.remove();
            Set<ES> neighbors = stateSpace.getNeighborStatesOf(current);
            neighbors.retainAll(devZoneStates);

            for (EntityState<P> neighbor : neighbors) {
                if (!distance.containsKey(neighbor)) {
                    D distToNeighbor = abstractNumberHandler.add(distance.get(current), abstractNumberHandler.getOne());
                    distance.put(neighbor, distToNeighbor);
                }
            }
        }

        return distance;
    }
}
