package pl.edu.agh.idziak.asw.wavefront;

import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.CostFunction;
import pl.edu.agh.idziak.asw.model.StateSpace;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface Wavefront<SS extends StateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>> {
    DeviationSubspacePlan<CS> buildPlanForDeviationSubspace(DeviationSubspace<CS> deviationSubspace, CostFunction<CS, D> costFunction);
}
