package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWOutputPlan<SS extends StateSpace<U>, GS extends GlobalState<U>, U extends Comparable<U>> implements OutputPlan<SS, GS, U> {
    private SS stateSpace;
    private GS initialState;
    private GS targetState;
    private List<Path<U>> paths;

    ASWOutputPlan(SS stateSpace, GS initialState, GS targetState, List<Path<U>> paths) {
        this.stateSpace = stateSpace;
        this.initialState = initialState;
        this.targetState = targetState;
        this.paths = paths;
    }

    @Override
    public List<Path<U>> getPaths() {
        return paths;
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
