package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.AlgorithmTestHelper;
import pl.edu.agh.idziak.asw.common.Triple;
import pl.edu.agh.idziak.asw.model.ASWOutputPlan;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Tomasz on 27.08.2016.
 */
public abstract class BaseGrid2DTest {
    private final G2DPlanner planner = new G2DPlanner();

    protected void executeGrid2DTest(List<Triple<?, G2DEntityState, G2DEntityState>> entities, int[][] stateSpaceArray) {
        // state space
        G2DStateSpace stateSpace = new G2DStateSpace(stateSpaceArray);

        // initial states
        G2DCollectiveState initialState = G2DCollectiveState.from(AlgorithmTestHelper.mapToInitialState(entities));
        G2DCollectiveState targetState = G2DCollectiveState.from(AlgorithmTestHelper.mapToTargetState(entities));

        G2DInputPlan inputPlan = new G2DInputPlan(
                AlgorithmTestHelper.mapToEntitiesSet(entities), stateSpace, initialState, targetState);

        // calculate plan
        ASWOutputPlan<G2DStateSpace, G2DCollectiveState> outputPlan = planner.calculatePlan(inputPlan);

        // print results
        double cost = G2DPathCostCalculator.calculateCost(outputPlan.getCollectivePath(), new G2DCostFunction());
        System.out.println("Path cost: " + cost);

        assertNotNull(outputPlan.getCollectivePath());
    }


}
