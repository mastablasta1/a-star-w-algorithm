package pl.edu.agh.idziak.asw.example;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.impl.grid2d.*;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;

import java.util.List;
import java.util.stream.Collectors;

public class GridUsageExample {
    public static Logger log = LoggerFactory.getLogger(GridUsageExample.class);

    @Test
    public void example1() throws Exception {
        GridASWPlanner gridASWPlanner = new GridASWPlanner();

        List<Integer> entities = ImmutableList.of(1, 2);

        GridCollectiveStateSpace stateSpace = new GridCollectiveStateSpace(new byte[][]{
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0}
        });

        GridCollectiveState initialCS = GridCollectiveState.fromEntityStates(
                GridEntityState.of(0, 0),
                GridEntityState.of(3, 3)
        );
        GridCollectiveState targetCS = GridCollectiveState.fromEntityStates(
                GridEntityState.of(3, 3),
                GridEntityState.of(0, 0)
        );

        GridInputPlan inputPlan = GridInputPlan.builder()
                .entities(entities)
                .collectiveStateSpace(stateSpace)
                .initialState(initialCS)
                .targetState(targetCS)
                .neighborhoodType(NeighborhoodType.VON_NEUMANN)
                .build();

        gridASWPlanner.setDeviationSubspaceLocator(
                new GridDeviationSubspaceLocator()
                        .setRiskProximity(2.0)
                        .setExpansionFactor(1)
        );

        GridASWOutputPlan outputPlan = gridASWPlanner.calculatePlan(inputPlan);

        String statesString = outputPlan.getCollectivePath()
                .get()
                .stream()
                .map(GridCollectiveState::toString)
                .collect(Collectors.joining(",\n"));

        log.info(statesString);

        DeviationSubspacePlan<GridCollectiveState> plan = outputPlan.getDeviationSubspacePlans()
                .iterator().next();

        GridCollectiveState currentState = GridCollectiveState.fromEntityStates(
                GridEntityState.of(1, 1));

        GridCollectiveDeviationSubspace gridSubspace =
                (GridCollectiveDeviationSubspace) plan.getDeviationSubspace();

        if(plan.containsState(currentState)){
            CollectivePath<GridCollectiveState> path = plan.getPathToGoalFrom(currentState);
        }
    }
}
