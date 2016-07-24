package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWOutputPlan<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> implements OutputPlan<SS, GS, P, D> {
    private SS stateSpace;
    private GS initialState;
    private GS targetState;
    private GlobalPath<P> path;

    ASWOutputPlan(SS stateSpace, GS initialState, GS targetState, GlobalPath<P> path) {
        this.stateSpace = stateSpace;
        this.initialState = initialState;
        this.targetState = targetState;
        this.path = path;
    }

    public GlobalPath<P> getGlobalPath() {
        return path;
    }

    @Override
    public Set<DeviationZone<P>> getDeviationZones() {
        return null;
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


}
