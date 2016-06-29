package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.InputPlan;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DInputPlan implements InputPlan<Grid2DGlobalStateSpace, Grid2DGlobalState, Integer> {
    @Override
    public Grid2DGlobalState getInitialGlobalState() {
        return null;
    }

    @Override
    public Grid2DGlobalState getTargetGlobalState() {
        return null;
    }

    @Override
    public Grid2DGlobalStateSpace getStateSpace() {
        return null;
    }
}
