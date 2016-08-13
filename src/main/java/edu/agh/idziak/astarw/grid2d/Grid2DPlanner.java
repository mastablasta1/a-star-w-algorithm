package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.algorithm.ASWPlanner;
import edu.agh.idziak.common.DoubleHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class Grid2DPlanner extends ASWPlanner<Grid2DStateSpace, Grid2DCollectiveState, Integer, Double> {

    private static final DoubleHandler DOUBLE_HANDLER = DoubleHandler.getInstance();

    public Grid2DPlanner() {
        super(DOUBLE_HANDLER);
    }
}
