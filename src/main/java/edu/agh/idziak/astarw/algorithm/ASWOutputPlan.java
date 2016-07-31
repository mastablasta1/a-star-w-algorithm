package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWOutputPlan<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> implements OutputPlan<SS, GS, P, D> {
    private SS stateSpace;
    private GS initialState;
    private GS targetState;
    private GlobalPath<P> path;
    private List<DeviationZone<P>> deviationZones;

    private ASWOutputPlan() {
    }

    private ASWOutputPlan(Builder<SS, GS, P, D> builder) {
        stateSpace = builder.stateSpace;
        initialState = builder.initialState;
        targetState = builder.targetState;
        path = builder.path;
        deviationZones = builder.deviationZones;
    }

    static <SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> Builder<SS, GS, P, D> builder() {
        return new Builder<>();
    }

    public GlobalPath<P> getGlobalPath() {
        return path;
    }

    @Override
    public List<DeviationZone<P>> getDeviationZones() {
        return deviationZones;
    }

    @Override
    public GS getInitialGlobalState() {
        return initialState;
    }

    @Override
    public GS getTargetGlobalState() {
        return targetState;
    }

    @Override
    public SS getStateSpace() {
        return stateSpace;
    }

    static final class Builder<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {
        private SS stateSpace;
        private GS initialState;
        private GS targetState;
        private GlobalPath<P> path;
        private List<DeviationZone<P>> deviationZones;

        private Builder() {
        }

        Builder<SS, GS, P, D> stateSpace(SS val) {
            stateSpace = val;
            return this;
        }

        Builder<SS, GS, P, D> initialState(GS val) {
            initialState = val;
            return this;
        }

        Builder<SS, GS, P, D> targetState(GS val) {
            targetState = val;
            return this;
        }

        Builder<SS, GS, P, D> path(GlobalPath<P> val) {
            path = val;
            return this;
        }

        Builder<SS, GS, P, D> deviationZones(List<DeviationZone<P>> val) {
            deviationZones = val;
            return this;
        }

        ASWOutputPlan<SS, GS, P, D> build() {
            return new ASWOutputPlan<>(this);
        }
    }
}
