package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.*;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.DeviationZonesFinder;
import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.*;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

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
        ImmutableSet.Builder<Subspace<G2DCollectiveState>> outputBuilder = ImmutableSet.builder();

        List<G2DCollectiveState> collectiveStates = collectivePath.get();

        PeekingIterator<G2DCollectiveState> pathIterator = Iterators.peekingIterator(collectiveStates.iterator());

        while (pathIterator.hasNext()) {
            G2DCollectiveState collectiveState = pathIterator.next();
            List<Map.Entry<?, G2DEntityState>> entriesList =
                    ImmutableList.copyOf(collectiveState.getEntityStates().entrySet());

            Set<Set<G2DEntityState>> finalProximateSets = buildSetsOfProximateEntities(entriesList);
            if (finalProximateSets.isEmpty())
                continue;

            Map<G2DEntityState, ?> stateToEntityMap = buildReversedEntityStatesMap(collectiveState);

            for (Set<G2DEntityState> proximateSet : finalProximateSets) {
                G2DCollectiveState collectiveState1 =
                        G2DCollectiveState.from(buildCollectiveStateMap(stateToEntityMap, proximateSet));

                Set<G2DCollectiveState> deviationZoneStatesSet =
                        inputPlan.getStateSpace().getNeighborStatesOf(collectiveState1);

                G2DSubspace newDeviationZone = new G2DSubspace(deviationZoneStatesSet, pathIterator.peek());
                outputBuilder.add(newDeviationZone);
            }

        }
        return outputBuilder.build();
    }

    private static Set<Set<G2DEntityState>> buildSetsOfProximateEntities(List<Map.Entry<?, G2DEntityState>> entriesList) {
        Set<Set<G2DEntityState>> finalProximateSets = new HashSet<>();
        Multimap<G2DEntityState, G2DEntityState> proximateStatesCache = buildProximateStatesCache(entriesList);

        // for (Collection<G2DEntityState> proximateStates : proximateStatesCache.asMap().values()) {
        //     if (proximateStates.size() < 2) {
        //         continue;
        //     }
        //
        //     for (Set<G2DEntityState> set : finalProximateSets) {
        //         if (set.containsAll(proximateStates) || proximateStates.containsAll(set)) {
        //             set.addAll(proximateStates);
        //         } else {
        //             finalProximateSets.add(new HashSet<>(proximateStates));
        //         }
        //     }
        //     boolean setAlreadyVisited = finalProximateSets.stream()
        //                                                   .filter(set -> set.containsAll(proximateStates)
        //                                                           || proximateStates.containsAll(set))
        //                                                   .map(set -> set.addAll(proximateStates))
        //                                                   .findAny()
        //                                                   .isPresent();
        //     if (!setAlreadyVisited) {
        //         finalProximateSets.add(new HashSet<>(proximateStates));
        //     }
        // }

        return proximateStatesCache.asMap()
                                   .values()
                                   .stream()
                                   .filter(set -> set.size() >= 2)
                                   .map(HashSet::new)
                                   .distinct().collect(toSet());
    }

    private static Map<?, G2DEntityState> buildCollectiveStateMap(Map<G2DEntityState, ?> stateToEntityMap, Set<G2DEntityState> proximateSet) {
        Map<Object, G2DEntityState> result = new HashMap<>();
        for (G2DEntityState state : proximateSet) {
            result.put(stateToEntityMap.get(state), state);
        }
        return result;
    }

    private static Map<G2DEntityState, ?> buildReversedEntityStatesMap(G2DCollectiveState collectiveState) {
        return collectiveState.getEntityStates().entrySet().stream().collect(
                toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    private static Multimap<G2DEntityState, G2DEntityState> buildProximateStatesCache(List<Map.Entry<?, G2DEntityState>> entriesList) {
        List<List<Map.Entry<?, G2DEntityState>>> combinations =
                Lists.cartesianProduct(ImmutableList.of(entriesList, entriesList));

        Multimap<G2DEntityState, G2DEntityState> proximateStatesCache = HashMultimap.create();

        for (List<Map.Entry<?, G2DEntityState>> combination : combinations) {
            G2DEntityState state1 = combination.get(0).getValue();
            G2DEntityState state2 = combination.get(1).getValue();
            int verticalDist = Math.abs(state1.getRow() - state2.getRow());
            int horizontalDist = Math.abs(state1.getCol() - state2.getCol());
            if (verticalDist + horizontalDist <= 2) {
                proximateStatesCache.put(state1, state2);
                proximateStatesCache.put(state2, state1);
            }
        }
        return proximateStatesCache;
    }
}
