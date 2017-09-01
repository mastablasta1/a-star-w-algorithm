package pl.edu.agh.idziak.asw.wavefront;

import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.DistanceHeuristic;
import pl.edu.agh.idziak.asw.model.CollectiveStateSpace;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface Wavefront<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState, D extends Comparable<D>> {
    DeviationSubspacePlan<CS> buildPlanForDeviationSubspace(DeviationSubspace<CS> deviationSubspace, DistanceHeuristic<CS, D> distanceHeuristic);
}
