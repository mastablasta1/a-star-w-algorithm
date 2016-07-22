package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.OutputPlan;
import edu.agh.idziak.common.Pair;
import edu.agh.idziak.common.SingleTypePair;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 17.07.2016.
 */
public class SimpleGrid2DTest {

    @Test
    public void test1() throws Exception {
        Grid2DPlanner planner = new Grid2DPlanner();

        List<SingleTypePair<Grid2DEntityState>> startsAndGoals = startsAndGoals();

        Grid2DStateSpace stateSpace = new Grid2DStateSpace(createStateSpace());
        Grid2DGlobalState initialState = Grid2DGlobalState.fromEntityStates(createInitialState(startsAndGoals));
        Grid2DGlobalState targetState = Grid2DGlobalState.fromEntityStates(createTargetState(startsAndGoals));

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

    private List<SingleTypePair<Grid2DEntityState>> startsAndGoals() {
        return ImmutableList.of(
                Pair.ofSingleType(Grid2DEntityState.of(0, 0), Grid2DEntityState.of(2, 2)),
                //Pair.ofSingleType(Grid2DEntityState.of(2, 2), Grid2DEntityState.of(0, 0)),
                Pair.ofSingleType(Grid2DEntityState.of(0, 2), Grid2DEntityState.of(2, 0))
        );
    }

    private List<EntityState<Integer>> createInitialState(List<SingleTypePair<Grid2DEntityState>> startsAndGoals) {
        return startsAndGoals.stream().map(Pair::getOne).collect(Collectors.toList());
    }

    private List<EntityState<Integer>> createTargetState(List<SingleTypePair<Grid2DEntityState>> startsAndGoals) {
        return startsAndGoals.stream().map(Pair::getTwo).collect(Collectors.toList());
    }
}
