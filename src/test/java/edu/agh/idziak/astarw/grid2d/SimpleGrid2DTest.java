package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.OutputPlan;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tomasz on 17.07.2016.
 */
public class SimpleGrid2DTest {

    @Test
    public void test1() throws Exception {
        Grid2DPlanner planner = new Grid2DPlanner();

        Grid2DStateSpace stateSpace = new Grid2DStateSpace(createStateSpace());
        Grid2DGlobalState initialState = Grid2DGlobalState.fromEntityStates(createInitialState());
        Grid2DGlobalState targetState = Grid2DGlobalState.fromEntityStates(createTargetState());

        Grid2DInputPlan inputPlan = new Grid2DInputPlan(stateSpace, initialState, targetState);

        OutputPlan<Grid2DStateSpace, Grid2DGlobalState, Integer> outputPlan = planner.calculatePlan(inputPlan);

        System.out.println(outputPlan.getGlobalPath());
    }

    private int[][] createStateSpace() {
        return new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    private List<EntityState<Integer>> createInitialState() {
        return Arrays.asList(
                new Grid2DEntityState(0, 0),
                new Grid2DEntityState(2, 2));
    }

    private List<EntityState<Integer>> createTargetState() {
        return Arrays.asList(
                new Grid2DEntityState(2, 2),
                new Grid2DEntityState(0, 0));
    }
}
