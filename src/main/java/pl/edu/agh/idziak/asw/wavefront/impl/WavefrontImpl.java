package pl.edu.agh.idziak.asw.wavefront.impl;


import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.model.AbstractNumberHandler;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.CostFunction;
import pl.edu.agh.idziak.asw.model.StateSpace;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;

import java.util.*;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class WavefrontImpl<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>> implements Wavefront<SS, CS, D> {

    private AbstractNumberHandler<D> abstractNumberHandler;

    public WavefrontImpl(AbstractNumberHandler<D> abstractNumberHandler) {
        this.abstractNumberHandler = abstractNumberHandler;
    }

    @Override
    public SubspacePlan<SS, CS> buildPlanForSubspace(Subspace<CS> subspace, SS stateSpace, CostFunction<CS, D> costFunction) {
        CS targetState = subspace.getTargetState();

        Queue<CS> queue = new LinkedList<>();
        Map<CS, D> distanceFromTarget = new HashMap<>();

        queue.add(targetState);
        distanceFromTarget.put(targetState, abstractNumberHandler.getZero());

        while (!queue.isEmpty()) {
            CS current = queue.remove();

            Set<CS> neighbors = stateSpace.getNeighborStatesOf(current);

            D distCurrentToTarget = distanceFromTarget.get(current);

            for (CS neighbor : neighbors) {
                if (!distanceFromTarget.containsKey(neighbor)) {
                    D distNeighborToCurrent = costFunction.getHeuristicCost(neighbor, current);
                    distanceFromTarget.put(neighbor, abstractNumberHandler.add(distCurrentToTarget, distNeighborToCurrent));
                    queue.add(neighbor);
                }
            }
        }
        return GradientSubspacePlan.from(subspace, distanceFromTarget,stateSpace);
    }

    @Override
    public SubspacePlan<SS, CS> buildPlanForSpace(CS targetState, SS stateSpace, CostFunction<CS, D> costFunction) {
        return buildPlanForSubspace(ImmutableSubspace.from(ImmutableSet.of(), targetState), stateSpace, costFunction);
    }

}
