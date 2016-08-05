package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWGlobalOutputPlan<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> implements GlobalOutputPlan<SS, GS, P, D> {
    private SS stateSpace;
    private GS initialState;
    private GS targetState;
    private GlobalPath<P> path;
    private List<EntityOutputPlan<P>> entityOutputPlans;

    private ASWGlobalOutputPlan() {
    }

    private ASWGlobalOutputPlan(Builder<SS, GS, P, D> builder) {
        stateSpace = builder.stateSpace;
        initialState = builder.initialState;
        targetState = builder.targetState;
        path = builder.path;
        entityOutputPlans = builder.entityOutputPlans;
    }

    static <SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> Builder<SS, GS, P, D> builder() {
        return new Builder<>();
    }

    public GlobalPath<P> getGlobalPath() {
        return path;
    }

    @Override
    public List<EntityOutputPlan<P>> getPlansForEntities() {
        return entityOutputPlans;
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

    @Override
    public int getEntitiesCount() {
        return initialState.getEntitiesCount();
    }

    static final class Builder<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {
        private SS stateSpace;
        private GS initialState;
        private GS targetState;
        private GlobalPath<P> path;
        private List<EntityOutputPlan<P>> entityOutputPlans;

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

        Builder<SS, GS, P, D> entityOutputPlans(List<EntityOutputPlan<P>> val) {
            entityOutputPlans = val;
            return this;
        }

        ASWGlobalOutputPlan<SS, GS, P, D> build() {
            return new ASWGlobalOutputPlan<>(this);
        }
    }
}
