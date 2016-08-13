package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.OutputPlan;
import edu.agh.idziak.astarw.grid2d.SimpleEntityFactory.SimpleEntity;
import edu.agh.idziak.common.Triple;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Tomasz on 17.07.2016.
 */
public class SimpleGrid2DTest {

    @Test
    public void test1() throws Exception {
        // given
        Grid2DPlanner planner = new Grid2DPlanner();

        List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> input = createPositionsAndTargets();

        // state space
        Grid2DStateSpace stateSpace = new Grid2DStateSpace(createStateSpace());

        // initial states
        Grid2DCollectiveState initialState = Grid2DCollectiveState.fromEntityStates(mapToInitialState(input));
        Grid2DCollectiveState targetState = Grid2DCollectiveState.fromEntityStates(mapToTargetState(input));

        Grid2DInputPlan inputPlan = new Grid2DInputPlan(mapToEntitiesSet(input), stateSpace, initialState, targetState);

        // when
        OutputPlan<Grid2DStateSpace, Grid2DCollectiveState, Integer, Double> outputPlan = planner.calculatePlan(inputPlan);

        // then
        assertNotNull(outputPlan.getCollectivePath());
    }

    private Set mapToEntitiesSet(List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> input) {
        return input.stream().map(Triple::getOne).collect(Collectors.toSet());
    }

    private Map<?, EntityState<Integer>> mapToInitialState(List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> input) {
        return input.stream().collect(Collectors.toMap(Triple::getOne, Triple::getTwo));
    }

    private Map<?, EntityState<Integer>> mapToTargetState(List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> input) {
        return input.stream().collect(Collectors.toMap(Triple::getOne, Triple::getThree));
    }

    private int[][] createStateSpace() {
        return new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    private List<Triple<SimpleEntity, Grid2DEntityState, Grid2DEntityState>> createPositionsAndTargets() {
        return ImmutableList.of(
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(0, 2), Grid2DEntityState.of(2, 0)),
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(2, 0), Grid2DEntityState.of(0, 2)),
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(2, 2), Grid2DEntityState.of(0, 0)),
                Triple.of(SimpleEntityFactory.create(), Grid2DEntityState.of(0, 0), Grid2DEntityState.of(2, 2))
        );
    }

}
