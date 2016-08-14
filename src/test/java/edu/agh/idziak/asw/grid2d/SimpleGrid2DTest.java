package edu.agh.idziak.asw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.asw.AlgorithmTestHelper;
import edu.agh.idziak.asw.OutputPlan;
import edu.agh.idziak.asw.grid2d.SimpleEntityFactory.SimpleEntity;
import edu.agh.idziak.common.Triple;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Tomasz on 17.07.2016.
 */
public class SimpleGrid2DTest {


    private final Grid2DPlanner planner = new Grid2DPlanner();
    private final Grid2DPathCostCalculator pathCostCalculator = new Grid2DPathCostCalculator();

    @Test
    public void test1() throws Exception {
        // given

        List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> input = createPositionsAndTargets();

        // state space
        Grid2DStateSpace stateSpace = new Grid2DStateSpace(createStateSpace());

        // initial states
        Grid2DCollectiveState initialState = Grid2DCollectiveState.fromEntityStates(AlgorithmTestHelper.mapToInitialState(input));
        Grid2DCollectiveState targetState = Grid2DCollectiveState.fromEntityStates(AlgorithmTestHelper.mapToTargetState(input));

        Grid2DInputPlan inputPlan = new Grid2DInputPlan(AlgorithmTestHelper.mapToEntitiesSet(input), stateSpace, initialState, targetState);

        // when
        OutputPlan<Grid2DStateSpace, Grid2DCollectiveState, Integer, Double> outputPlan = planner.calculatePlan(inputPlan);

        // then
        double cost = Grid2DPathCostCalculator.calculateCost(outputPlan.getCollectivePath(), stateSpace);
        System.out.println("Path cost: " + cost);



        assertNotNull(outputPlan.getCollectivePath());
    }

    private static int[][] createStateSpace() {
        return new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    private static List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> createPositionsAndTargets() {
        return ImmutableList.of(
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(0, 2), Grid2DEntityState.of(2, 0)),
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(2, 0), Grid2DEntityState.of(0, 2)),
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(2, 2), Grid2DEntityState.of(0, 0)),
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(0, 0), Grid2DEntityState.of(2, 2))
        );
    }

}
