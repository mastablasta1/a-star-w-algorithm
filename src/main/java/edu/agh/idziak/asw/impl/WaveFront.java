package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.*;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class WaveFront<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> {

    private AbstractNumberHandler<D> abstractNumberHandler;

    public WaveFront(AbstractNumberHandler<D> abstractNumberHandler) {
        this.abstractNumberHandler = abstractNumberHandler;
    }

    public Set<DeviationZonePlan<CS, ES, P>> makeDeviationZonePlans(PlanningData<SS, CS, ES, P, D> planningData) {
        Set<DeviationZone<CS, ES, P>> deviationZones = planningData.getDeviationZones();
        SS stateSpace = planningData.getInputPlan().getStateSpace();

        Set<DeviationZonePlan<CS, ES, P>> result = new HashSet<>();

        for (DeviationZone<CS, ES, P> deviationZone : deviationZones) {
            result.add(buildPlanForDevZone(deviationZone, stateSpace));
        }

        return result;
    }

    private DeviationZonePlan<CS, ES, P> buildPlanForDevZone(DeviationZone<CS, ES, P> deviationZone, SS stateSpace) {
        CS targetState = deviationZone.getTargetState();

        Queue<CS> queue = new LinkedList<>();
        Map<CS, D> distance = new HashMap<>();

        queue.add(targetState);

        while (!queue.isEmpty()) {
            CS current = queue.remove();

            Set<CS> neighbors = stateSpace.getNeighborStatesOf(current);

            for (CS neighbor : neighbors) {
                if (!distance.containsKey(neighbor)) {
                    D distNeighborToCurrent = stateSpace.getHeuristicCost(neighbor, current);
                    D distForCurrent = distance.get(current);
                    distance.put(neighbor, abstractNumberHandler.add(distForCurrent, distNeighborToCurrent));
                    queue.add(neighbor);
                }
            }
        }
        return new ImmutableDeviationZonePlan<>(deviationZone, stateSpace, distance, abstractNumberHandler);
    }

}
