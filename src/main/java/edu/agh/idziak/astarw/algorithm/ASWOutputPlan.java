package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWOutputPlan<SS extends StateSpace<U>, GS extends GlobalState<U>, U extends Comparable<U>> implements OutputPlan<SS, GS, U> {
    private SS stateSpace;
    private GS initialState;
    private GS targetState;
    private GlobalPath<U> path;

    ASWOutputPlan(SS stateSpace, GS initialState, GS targetState, GlobalPath<U> path) {
        this.stateSpace = stateSpace;
        this.initialState = initialState;
        this.targetState = targetState;
        this.path = path;
    }

    public GlobalPath<U> getGlobalPath() {
        return path;
    }

    @Override
    public Set<DeviationZone<U>> getDeviationZones() {
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
