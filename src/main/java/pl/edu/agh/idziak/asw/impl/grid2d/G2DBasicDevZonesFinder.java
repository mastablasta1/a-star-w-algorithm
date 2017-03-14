package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.DeviationZonesFinder;
import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 13.03.2017.
 */
public class G2DBasicDevZonesFinder implements DeviationZonesFinder<G2DInputPlan, G2DCollectiveState> {

    @Override
    public Set<Subspace<G2DCollectiveState>> findDeviationZones(G2DInputPlan inputPlan, CollectivePath<G2DCollectiveState> collectivePath) {
        ImmutableSet.Builder<Subspace<G2DCollectiveState>> deviationZonesAccumulator = ImmutableSet.builder();
        Map<Set<?>, Set<G2DEntityState>> devZonesCache = new HashMap<>();

        for (G2DCollectiveState collectiveState : collectivePath.get()) {
            List<List<Map.Entry<?, G2DEntityState>>> setsOfProximateStates = buildSetsOfProximateStates(collectiveState);

            Iterator<Map.Entry<Set<?>, Set<G2DEntityState>>> devZoneCacheIterator = devZonesCache.entrySet().iterator();

            while (devZoneCacheIterator.hasNext()) {
                Map.Entry<Set<?>, Set<G2DEntityState>> cacheEntry = devZoneCacheIterator.next();
                Set<?> entitySet = cacheEntry.getKey();
                Set<G2DEntityState> stateSet = cacheEntry.getValue();
                List<Map.Entry<?, G2DEntityState>> matchingOpenSet =
                        findMatchingOpenSet(setsOfProximateStates, entitySet);

                if (matchingOpenSet != null) {
                    matchingOpenSet.stream()
                                   .map(Map.Entry::getValue)
                                   .forEach(stateSet::add);
                    setsOfProximateStates.remove(matchingOpenSet);
                } else {
                    finalizeDeviationZone(entitySet, stateSet);
                    devZoneCacheIterator.remove();
                }
            }

            setsOfProximateStates.forEach(entries -> {
                Set<?> entitiesSet = entries.stream().map(Map.Entry::getKey).collect(Collectors.toSet());
                Set<G2DEntityState> stateSet = entries.stream()
                                                      .map(Map.Entry::getValue)
                                                      .collect(Collectors.toSet());
                devZonesCache.put(entitiesSet, stateSet);
            });


        }

        return null;
    }

    private static List<Map.Entry<?, G2DEntityState>> findMatchingOpenSet(List<List<Map.Entry<?, G2DEntityState>>> setsOfProximateStates, Set<?> entitySet) {
        return setsOfProximateStates.stream()
                                    .filter(setOfProximateStates -> {
                                        if (setOfProximateStates.size() != entitySet.size()) {
                                            return false;
                                        }
                                        long matchingElements = setOfProximateStates.stream()
                                                                                    .map(Map.Entry::getKey)
                                                                                    .filter(entitySet::contains)
                                                                                    .count();
                                        return matchingElements == entitySet.size();
                                    })
                                    .findFirst()
                                    .orElse(null);
    }

    private void finalizeDeviationZone(Set<?> entitySet, Set<G2DEntityState> stateSet) {

    }

    private static List<List<Map.Entry<?, G2DEntityState>>> buildSetsOfProximateStates(G2DCollectiveState collectiveState) {
        List<List<Map.Entry<?, G2DEntityState>>> setsOfProximateStates = new ArrayList<>();

        for (Map.Entry<?, G2DEntityState> entry : collectiveState.getEntityStates().entrySet()) {
            G2DEntityState entityState = entry.getValue();
            List<Map.Entry<?, G2DEntityState>> existingSetToExpand = null;
            outsideLoop:
            for (List<Map.Entry<?, G2DEntityState>> setOfProximateStates : setsOfProximateStates) {
                for (Map.Entry<?, G2DEntityState> stateEntry : setOfProximateStates) {
                    G2DEntityState cachedState = stateEntry.getValue();
                    if (areStatesProximate(entityState, cachedState)) {
                        existingSetToExpand = setOfProximateStates;
                        break outsideLoop;
                    }
                }
            }
            if (existingSetToExpand != null) {
                existingSetToExpand.add(entry);
            } else {
                List<Map.Entry<?, G2DEntityState>> newProximateSet = new LinkedList<>();
                newProximateSet.add(entry);
                setsOfProximateStates.add(newProximateSet);
            }
        }
        return setsOfProximateStates;
    }

    private static boolean areStatesProximate(G2DEntityState state1, G2DEntityState state2) {
        int manhattanDistance = Math.abs(state1.getCol() - state2.getCol())
                + Math.abs(state1.getRow() - state2.getRow());
        return manhattanDistance <= 2;
    }

    private class DeviationZoneBuilder {
        private Map<?, G2DEntityState> destinationStates;
        private Set<G2DEntityState> allEntityStates;
    }
}
