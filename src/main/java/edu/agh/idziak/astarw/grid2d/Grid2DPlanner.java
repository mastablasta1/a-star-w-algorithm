package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.algorithm.ASWPlanner;
import edu.agh.idziak.common.IntegerHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class Grid2DPlanner extends ASWPlanner<Grid2DStateSpace, Grid2DGlobalState, Integer> {

    private static final IntegerHandler integerHandler = IntegerHandler.getInstance();

    public Grid2DPlanner() {
        super(integerHandler);
    }
}
