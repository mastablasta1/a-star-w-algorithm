package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.OutputPlan;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tomasz on 17.07.2016.
 */
public class SimpleGrid2DTest {

    @Test
    public void test1() throws Exception {
        Grid2DPlanner planner = new Grid2DPlanner();

        Grid2DStateSpace stateSpace = new Grid2DStateSpace(createStateSpace());
        Grid2DGlobalState initialState = Grid2DGlobalState.from(createInitialState());
        Grid2DGlobalState targetState = Grid2DGlobalState.from(createTargetState());

        Grid2DInputPlan inputPlan = new Grid2DInputPlan(stateSpace, initialState, targetState);

        OutputPlan<Grid2DStateSpace, Grid2DGlobalState, Integer> outputPlan = planner.calculatePlan(inputPlan);

        outputPlan.getPaths().forEach(System.out::println);
    }

    private int[][] createStateSpace() {
        return new int[][]{
                {1, 1, 1},
                {1, 2, 1},
                {1, 2, 1}
        };
    }

    private List<EntityState<Integer>> createInitialState() {
        Grid2DEntityState state1 = new Grid2DEntityState(0, 0);
        Grid2DEntityState state2 = new Grid2DEntityState(0, 2);
        return ImmutableList.of(state1, state2);
    }

    private List<EntityState<Integer>> createTargetState() {
        Grid2DEntityState state1 = new Grid2DEntityState(2, 2);
        Grid2DEntityState state2 = new Grid2DEntityState(2, 0);
        return ImmutableList.of(state1, state2);
    }
}
