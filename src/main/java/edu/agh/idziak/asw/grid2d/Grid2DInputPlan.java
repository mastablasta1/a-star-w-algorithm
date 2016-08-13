package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.InputPlan;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DInputPlan implements InputPlan<Grid2DStateSpace, Grid2DCollectiveState, Integer, Double> {

    private final Grid2DCollectiveState initialState;
    private final Grid2DCollectiveState targetState;
    private final Grid2DStateSpace globalStateSpace;
    private Set<?> entities;

    public Grid2DInputPlan(Set entities,
                           Grid2DStateSpace globalStateSpace,
                           Grid2DCollectiveState initialState,
                           Grid2DCollectiveState targetState) {
        this.entities = entities;
        this.globalStateSpace = globalStateSpace;
        this.targetState = targetState;
        this.initialState = initialState;
    }

    @Override
    public Grid2DCollectiveState getInitialGlobalState() {
        return initialState;
    }

    @Override
    public Grid2DCollectiveState getTargetGlobalState() {
        return targetState;
    }

    @Override
    public Grid2DStateSpace getStateSpace() {
        return globalStateSpace;
    }

    @Override
    public Set<?> getEntities() {
        return entities;
    }

}
