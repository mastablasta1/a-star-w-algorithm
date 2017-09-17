package pl.edu.agh.idziak.asw.wavefront.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.model.AbstractNumberHandler;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.CollectiveStateSpace;
import pl.edu.agh.idziak.asw.model.DistanceHeuristic;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;

import java.util.*;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class WavefrontImpl<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState, D extends Comparable<D>> implements Wavefront<CS, D> {

    private AbstractNumberHandler<D> abstractNumberHandler;
    private static final Logger LOG = LoggerFactory.getLogger(WavefrontImpl.class);

    public WavefrontImpl(AbstractNumberHandler<D> abstractNumberHandler) {
        this.abstractNumberHandler = abstractNumberHandler;
    }

    @Override
    public DeviationSubspacePlan<CS> buildPlanForSubspace(DeviationSubspace<CS> deviationSubspace, DistanceHeuristic<CS, D> distanceHeuristic) {
        CS targetState = deviationSubspace.getTargetState();
        long startTime = System.nanoTime();

        Queue<CS> queue = new LinkedList<>();
        Map<CS, D> distanceFromTarget = new HashMap<>();

        queue.add(targetState);
        distanceFromTarget.put(targetState, abstractNumberHandler.getZero());

        while (!queue.isEmpty()) {
            CS current = queue.remove();

            Collection<CS> neighbors = deviationSubspace.getNeighborStatesOf(current);

            D distCurrentToTarget = distanceFromTarget.get(current);

            if (Thread.interrupted()) {
                throw new RuntimeException(new InterruptedException("Wavefront iteration interrupted"));
            }
            for (CS neighbor : neighbors) {
                if (!distanceFromTarget.containsKey(neighbor)) {
                    D distNeighborToCurrent = distanceHeuristic.getDistanceBetween(neighbor, current);
                    distanceFromTarget.put(neighbor,
                            abstractNumberHandler.add(distCurrentToTarget, distNeighborToCurrent));
                    queue.add(neighbor);
                }
            }
        }

        GradientDeviationSubspacePlan<CS, D> subspace = new GradientDeviationSubspacePlan<>(deviationSubspace, distanceFromTarget);
        long elapsedTime = (System.nanoTime() - startTime) / 1000;
        LOG.info("Subspace of order " + targetState.size() + " calculated in " + elapsedTime + " microsec, collectiveStates = " + subspace.size());

        return subspace;
    }

}
