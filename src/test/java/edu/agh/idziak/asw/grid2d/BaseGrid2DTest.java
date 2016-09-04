package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.AlgorithmTestHelper;
import edu.agh.idziak.asw.OutputPlan;
import edu.agh.idziak.common.Triple;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Tomasz on 27.08.2016.
 */
public abstract class BaseGrid2DTest {
    private final G2DPlanner planner = new G2DPlanner();

    protected void executeGrid2DTest(List<Triple<?, G2DEntityState, G2DEntityState>> entities, int[][] stateSpaceArray) {
        // given

        // state space
        G2DStateSpace stateSpace = new G2DStateSpace(stateSpaceArray);

        // initial states
        G2DCollectiveState initialState = G2DCollectiveState.fromEntityStates(AlgorithmTestHelper.mapToInitialState(entities));
        G2DCollectiveState targetState = G2DCollectiveState.fromEntityStates(AlgorithmTestHelper.mapToTargetState(entities));

        G2DInputPlan inputPlan = new G2DInputPlan(AlgorithmTestHelper.mapToEntitiesSet(entities), stateSpace, initialState, targetState);

        // when
        OutputPlan<G2DStateSpace, G2DCollectiveState, G2DEntityState, Integer, Double> outputPlan = planner.calculatePlan(inputPlan);

        // then
        double cost = G2DPathCostCalculator.calculateCost(outputPlan.getCollectivePath(), stateSpace);
        System.out.println("Path cost: " + cost);


        assertNotNull(outputPlan.getCollectivePath());
    }


}
