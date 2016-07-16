package edu.agh.idziak.astarw;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.grid2d.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class MainTest {

    @Test
    public void test1() throws Exception {
        Grid2DPlanner planner = new Grid2DPlanner();

        Grid2DStateSpace stateSpace = new Grid2DStateSpace();
        Grid2DGlobalState initialState = Grid2DGlobalState.from(createInitialState());
        Grid2DGlobalState targetState = Grid2DGlobalState.from(createTargetState());

        Grid2DInputPlan inputPlan = new Grid2DInputPlan(stateSpace, initialState, targetState);

        OutputPlan<Grid2DStateSpace, Grid2DGlobalState, Integer> outputPlan = planner.calculatePlan(inputPlan);

        outputPlan.getPaths().forEach(System.out::println);
    }

    private List<EntityState<Integer>> createInitialState() {
        Grid2DEntityState state1 = new Grid2DEntityState(0, 0);
        return ImmutableList.of(state1);
    }

    private List<EntityState<Integer>> createTargetState() {
        Grid2DEntityState state1 = new Grid2DEntityState(3, 3);
        return ImmutableList.of(state1);
    }


}