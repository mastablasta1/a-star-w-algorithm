package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.logic.ASWPlanner;
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
