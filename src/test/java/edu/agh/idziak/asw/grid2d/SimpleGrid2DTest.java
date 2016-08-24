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


    private final G2DPlanner planner = new G2DPlanner();


    @Test
    public void test1() throws Exception {
        // given

        List<Triple<SimpleEntity, G2DEntityState, G2DEntityState>> input = createPositionsAndTargets();

        // state space
        G2DStateSpace stateSpace = new G2DStateSpace(createStateSpace());

        // initial states
        G2DCollectiveState initialState = G2DCollectiveState.fromEntityStates(AlgorithmTestHelper.mapToInitialState(input));
        G2DCollectiveState targetState = G2DCollectiveState.fromEntityStates(AlgorithmTestHelper.mapToTargetState(input));

        G2DInputPlan inputPlan = new G2DInputPlan(AlgorithmTestHelper.mapToEntitiesSet(input), stateSpace, initialState, targetState);

        // when
        OutputPlan<G2DStateSpace, G2DCollectiveState, G2DEntityState, Integer, Double> outputPlan = planner.calculatePlan(inputPlan);

        // then
        double cost = G2DPathCostCalculator.calculateCost(outputPlan.getCollectivePath(), stateSpace);
        System.out.println("Path cost: " + cost);


        assertNotNull(outputPlan.getCollectivePath());
    }

    private static int[][] createStateSpace() {
        return new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
    }

    private static List<Triple<SimpleEntity, G2DEntityState, G2DEntityState>> createPositionsAndTargets() {
        return ImmutableList.of(
                Triple.of(SimpleEntityFactory.create(), G2DEntityState.of(0, 0), G2DEntityState.of(4, 4)),
                Triple.of(SimpleEntityFactory.create(), G2DEntityState.of(4, 4), G2DEntityState.of(0, 0))
        );
    }

}
