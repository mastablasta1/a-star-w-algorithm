package pl.edu.agh.idziak.asw.impl;

import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;
import pl.edu.agh.idziak.asw.wavefront.impl.WavefrontImpl;

import java.util.Collection;

/**
 * Created by Tomasz on 21.02.2017.
 */
public abstract class BaseWavefrontPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends CollectiveStateSpace<CS>, CS extends CollectiveState, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final Wavefront<CS, D> wavefront;

    public BaseWavefrontPlanner(AbstractNumberHandler<D> numberHandler) {
        wavefront = new WavefrontImpl<>(numberHandler);
    }

    @Override
    public ASWOutputPlan<CS> calculatePlan(IP inputPlan) {
        StateSpaceAsDeviationSubspace<SS, CS> stateSpaceAsDeviationSubspace = new StateSpaceAsDeviationSubspace<>(
                inputPlan.getCollectiveStateSpace(),
                inputPlan.getTargetCollectiveState());

        DeviationSubspacePlan<CS> deviationSubspacePlan =
                wavefront.buildPlanForSubspace(
                        stateSpaceAsDeviationSubspace,
                        inputPlan.getDistanceHeuristic());

        CollectivePath<CS> collectivePath = deviationSubspacePlan.getPathToGoalFrom(inputPlan.getInitialCollectiveState());

        return ImmutableASWOutputPlan.from(collectivePath, ImmutableSet.of(deviationSubspacePlan));
    }

    public static class StateSpaceAsDeviationSubspace<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState> implements DeviationSubspace<CS> {
        private SS stateSpace;
        private CS targetState;

        public StateSpaceAsDeviationSubspace(SS stateSpace, CS targetState) {
            this.stateSpace = stateSpace;
            this.targetState = targetState;
        }

        @Override
        public Collection<CS> getNeighborStatesOf(CS globalState) {
            return stateSpace.getNeighborStatesOf(globalState);
        }

        public SS getStateSpace() {
            return stateSpace;
        }

        @Override
        public CS getTargetState() {
            return targetState;
        }
    }

}
