package pl.edu.agh.idziak.asw.grid2d;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.CollectivePath;
import pl.edu.agh.idziak.asw.DeviationZone;
import pl.edu.agh.idziak.asw.DeviationZonesFinder;
import pl.edu.agh.idziak.asw.impl.PlanningData;
import pl.edu.agh.idziak.common.PairCombinator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tomasz on 14.08.2016.
 */
public class G2DDeviationZonesFinder implements DeviationZonesFinder<G2DStateSpace, G2DCollectiveState,
        G2DEntityState, Double, Integer> {

    private int warningProximity;

    G2DDeviationZonesFinder(int warningProximity) {
        this.warningProximity = warningProximity;
    }

    @Override
    public Set<DeviationZone<G2DCollectiveState, G2DEntityState, Integer>> findDeviationZones
            (PlanningData<G2DStateSpace, G2DCollectiveState, G2DEntityState, Integer, Double> planningData) {
        Set<DeviationZone<G2DCollectiveState, G2DEntityState, Integer>> resultSet = new HashSet<>();
        G2DStateSpace stateSpace = planningData.getInputPlan().getStateSpace();
        CollectivePath<G2DCollectiveState, G2DEntityState, Integer> collectivePath = planningData.getCollectivePath();
        List<G2DCollectiveState> collectiveStates = collectivePath.get();

        for (int i = 0, size = collectiveStates.size(); i < size; i++) {
            G2DCollectiveState currentState = collectiveStates.get(i);

            Map<?, G2DEntityState> entityStates = currentState.getEntityStates();
            PairCombinator<?> pairCombinator = new PairCombinator<>(ImmutableList.copyOf(entityStates.keySet()));
            while (pairCombinator.hasNext()) {
                pairCombinator.next();

                Object firstEntity = pairCombinator.getCurrentFirst();
                Object secondEntity = pairCombinator.getCurrentSecond();
                G2DEntityState firstState = entityStates.get(firstEntity);
                G2DEntityState secondState = entityStates.get(secondEntity);

                Double dist = stateSpace.getHeuristicCost(firstState, secondState);

                if (dist <= warningProximity) {
                    Set<G2DEntityState> deviationZoneStates = buildDeviationZoneStatesSet(stateSpace, firstState,
                            secondState);

                    G2DEntityState firstTarget = findTargetStateForEntity(i, firstEntity, collectiveStates,
                            deviationZoneStates);
                    G2DEntityState secondTarget = findTargetStateForEntity(i, secondEntity, collectiveStates,
                            deviationZoneStates);

                    ImmutableMap<?, G2DEntityState> targetState = ImmutableMap.of(
                            firstEntity, firstTarget,
                            secondEntity, secondTarget);

                    G2DDeviationZone devZone = new G2DDeviationZone(deviationZoneStates, G2DCollectiveState
                            .fromEntityStates(targetState));
                    resultSet.add(devZone);
                }

            }
        }
        return resultSet;
    }

    private static Set<G2DEntityState> buildDeviationZoneStatesSet(G2DStateSpace stateSpace, G2DEntityState
            firstState, G2DEntityState secondState) {
        return ImmutableSet.<G2DEntityState>builder()
                .addAll(stateSpace.getNeighborStatesOf(firstState))
                .addAll(stateSpace.getNeighborStatesOf(secondState))
                .build();
    }

    private static G2DEntityState findTargetStateForEntity(int i, Object entity, List<G2DCollectiveState>
            collectiveStates, Set<G2DEntityState> deviationZoneStates) {
        G2DEntityState furthestStateOnPathWithinDevZone = collectiveStates.get(i).getStateForEntity(entity);

        G2DEntityState currentState;
        for (int j = i, size = collectiveStates.size(); j < size; j++) {
            currentState = collectiveStates.get(j).getStateForEntity(entity);
            if (!deviationZoneStates.contains(currentState)) {
                break;
            }
            furthestStateOnPathWithinDevZone = currentState;
        }
        return furthestStateOnPathWithinDevZone;
    }


}
