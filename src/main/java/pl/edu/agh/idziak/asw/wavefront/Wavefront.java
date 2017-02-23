package pl.edu.agh.idziak.asw.wavefront;

import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.CostFunction;
import pl.edu.agh.idziak.asw.model.StateSpace;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface Wavefront<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>> {
    SubspacePlan<SS, CS> buildPlanForSubspace(Subspace<CS> subspace, SS stateSpace, CostFunction<CS, D> costFunction);
    SubspacePlan<SS, CS> buildPlanForSpace(CS targetState, SS stateSpace, CostFunction<CS, D> costFunction);
}
