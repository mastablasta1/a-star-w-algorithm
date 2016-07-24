package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.InputPlan;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DInputPlan implements InputPlan<Grid2DStateSpace, Grid2DGlobalState, Integer, Double> {

    private final Grid2DGlobalState initialState;
    private final Grid2DGlobalState targetState;
    private final Grid2DStateSpace globalStateSpace;

    public Grid2DInputPlan(Grid2DStateSpace globalStateSpace,
                           Grid2DGlobalState initialState,
                           Grid2DGlobalState targetState) {
        this.globalStateSpace = globalStateSpace;
        this.targetState = targetState;
        this.initialState = initialState;
    }

    @Override
    public Grid2DGlobalState getInitialGlobalState() {
        return initialState;
    }

    @Override
    public Grid2DGlobalState getTargetGlobalState() {
        return targetState;
    }

    @Override
    public Grid2DStateSpace getStateSpace() {
        return globalStateSpace;
    }


}
