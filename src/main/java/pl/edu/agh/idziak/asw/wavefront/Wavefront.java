package pl.edu.agh.idziak.asw.wavefront;

import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.DistanceHeuristic;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface Wavefront<CS extends CollectiveState, D extends Comparable<D>> {
    DeviationSubspacePlan<CS> buildPlanForSubspace(DeviationSubspace<CS> deviationSubspace, DistanceHeuristic<CS, D> distanceHeuristic);
}
