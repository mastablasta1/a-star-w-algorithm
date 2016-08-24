package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tomasz on 13.08.2016.
 */
class ImmutableDeviationZonePlan<CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> implements DeviationZonePlan<CS, ES, P> {

    private DeviationZone<CS, ES, P> deviationZone;
    private StateSpace<CS, ES, D, P> stateSpace;
    private Map<CS, D> distances;
    private AbstractNumberHandler<D> abstractNumberHandler;

    ImmutableDeviationZonePlan(DeviationZone<CS, ES, P> deviationZone, StateSpace<CS, ES, D, P> stateSpace, Map<CS, D> distances, AbstractNumberHandler<D> abstractNumberHandler) {
        this.deviationZone = deviationZone;
        this.stateSpace = stateSpace;
        this.distances = distances;
        this.abstractNumberHandler = abstractNumberHandler;
    }

    @Override
    public DeviationZone<CS, ES, P> getDeviationZone() {
        return deviationZone;
    }

    @Override
    public CS getNextMove(CS collectiveState) {
        if (collectiveState.equals(deviationZone.getTargetState())) {
            return deviationZone.getTargetState();
        }

        Set<CS> neighbors = stateSpace.getNeighborStatesOf(collectiveState);
        neighbors.removeIf(cs -> !deviationZone.getStates().containsAll(cs.getEntityStates().values()));

        Iterator<CS> it = neighbors.iterator();
        if (!it.hasNext()) {
            return null;
        }

        CS first = it.next();

        CS bestState = first;
        D bestDist = calculateFullDistance(collectiveState, first);

        while (it.hasNext()) {
            CS next = it.next();
            D nextDist = calculateFullDistance(collectiveState, next);
            if (abstractNumberHandler.lessThan(nextDist, bestDist)) {
                bestState = next;
                bestDist = nextDist;
            }
        }

        return bestState;
    }

    private D calculateFullDistance(CS start, CS end) {
        return abstractNumberHandler.add(distances.get(end), stateSpace.getHeuristicCost(start, end));
    }
}
