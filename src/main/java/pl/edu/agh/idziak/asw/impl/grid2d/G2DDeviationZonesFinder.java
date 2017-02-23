package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.DeviationZonesFinder;
import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.Set;

/**
 * Created by Tomasz on 14.08.2016.
 */
public class G2DDeviationZonesFinder implements DeviationZonesFinder<G2DInputPlan, G2DCollectiveState> {

    private int warningProximity;

    G2DDeviationZonesFinder(int warningProximity) {
        this.warningProximity = warningProximity;
    }

    @Override
    public Set<Subspace<G2DCollectiveState>> findDeviationZones(G2DInputPlan inputPlan, CollectivePath<G2DCollectiveState> collectivePath) {
        return null;
    }


    // @Override
    // public Set<Subspace<G2DCollectiveState>> findDeviationZones(G2DInputPlan inputPlan, CollectivePath<G2DCollectiveState> collectivePath) {
    //     Set<Subspace<G2DCollectiveState>> resultSet = new HashSet<>();
    //     G2DStateSpace stateSpace = inputPlan.getStateSpace();
    //     List<G2DCollectiveState> collectiveStates = collectivePath.get();
    //
    //     for (int i = 0, size = collectiveStates.size(); i < size; i++) {
    //         G2DCollectiveState currentState = collectiveStates.get(i);
    //
    //         Map<?, G2DEntityState> entityStates = currentState.getEntityStates();
    //         PairCombinator<?> pairCombinator = new PairCombinator<>(ImmutableList
    //                 .copyOf(entityStates.keySet()));
    //         while (pairCombinator.hasNext()) {
    //             pairCombinator.next();
    //
    //             Object firstEntity = pairCombinator.getCurrentFirst();
    //             Object secondEntity = pairCombinator.getCurrentSecond();
    //             G2DEntityState firstState = entityStates.get(firstEntity);
    //             G2DEntityState secondState = entityStates.get(secondEntity);
    //
    //             Double dist = G2DCostFunction.getHeuristicCost(firstState, secondState);
    //
    //             if (dist <= warningProximity) {
    //                 Set<G2DEntityState> deviationZoneStates =
    //                         buildDeviationZoneStatesSet(stateSpace, firstState, secondState);
    //
    //                 G2DEntityState firstTarget =
    //                         findTargetStateForEntity(i, firstEntity, collectiveStates, deviationZoneStates);
    //                 G2DEntityState secondTarget =
    //                         findTargetStateForEntity(i, secondEntity, collectiveStates, deviationZoneStates);
    //
    //                 ImmutableMap<?, G2DEntityState> targetState =
    //                         ImmutableMap.of(firstEntity, firstTarget, secondEntity, secondTarget);
    //
    //                 G2DSubspace devZone = new G2DSubspace(
    //                         deviationZoneStates, new G2DCollectiveState(targetState));
    //                 resultSet.add(devZone);
    //             }
    //
    //         }
    //     }
    //     return resultSet;
    //
    // }
    //
    // private static Set<G2DEntityState> buildDeviationZoneStatesSet(G2DStateSpace
    //         stateSpace,
    //         G2DEntityState
    //                 firstState, G2DEntityState secondState) {
    //     return ImmutableSet.<G2DEntityState>builder()
    //             .addAll(stateSpace.getNeighborStatesOf(firstState))
    //             .addAll(stateSpace.getNeighborStatesOf(secondState))
    //             .build();
    // }
    //
    // private static G2DEntityState findTargetStateForEntity(int i, Object entity,
    //         List<G2DCollectiveState> collectiveStates, Set<G2DEntityState> deviationZoneStates) {
    //
    //     G2DEntityState furthestStateOnPathWithinDevZone =
    //             collectiveStates.get(i).getStateForEntity(entity);
    //
    //     G2DEntityState currentState;
    //     for (int j = i, size = collectiveStates.size(); j < size; j++) {
    //         currentState = collectiveStates.get(j).getStateForEntity(entity);
    //         if (!deviationZoneStates.contains(currentState)) {
    //             break;
    //         }
    //         furthestStateOnPathWithinDevZone = currentState;
    //     }
    //     return furthestStateOnPathWithinDevZone;
    // }
}
