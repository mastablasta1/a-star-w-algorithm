package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.DeviationSubspaceLocator;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 13.03.2017.
 */
public class GridDeviationSubspaceLocator implements DeviationSubspaceLocator<GridInputPlan, GridCollectiveState> {
    private static final double DEFAULT_RISK_PROXIMITY = 2d;
    private static final int DEFAULT_EXPANSION_FACTOR = 2;
    private static final Logger LOG = LoggerFactory.getLogger(GridDeviationSubspaceLocator.class);

    private double riskProximity = DEFAULT_RISK_PROXIMITY;
    private int expansionFactor = DEFAULT_EXPANSION_FACTOR;

    @Override
    public Collection<? extends DeviationSubspace<GridCollectiveState>> findDeviationSubspaces(GridInputPlan inputPlan, CollectivePath<GridCollectiveState> collectivePath) {
        long startTime = System.nanoTime();
        Accumulator acc = new Accumulator(inputPlan, collectivePath);

        for (GridCollectiveState colState : collectivePath.get()) {
            Set<BucketOfStates> buckets = findBucketsOfProximateStates(acc, colState);

            for (Iterator<OpenDeviationSubspace> ssIt = acc.openDevSubspaces.iterator(); ssIt.hasNext(); ) {
                OpenDeviationSubspace openSubspace = ssIt.next();

                boolean matchFound = false;
                for (Iterator<BucketOfStates> bIt = buckets.iterator(); bIt.hasNext(); ) {
                    BucketOfStates bucket = bIt.next();
                    Map<Object, GridEntityState> entitiesToStates = bucketStatesToEntities(acc, colState, bucket);
                    boolean matches = entitySetMatches(openSubspace, entitiesToStates.keySet());
                    if (matches) {
                        entitiesToStates.forEach(openSubspace.entitiesWithPaths::put);
                        bIt.remove();
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    finalizeDeviationSubspace(acc, openSubspace, colState);
                    ssIt.remove();
                }
            }

            buckets.forEach(bucket -> {
                OpenDeviationSubspace newOpenSubspace = new OpenDeviationSubspace();
                Map<Object, GridEntityState> map = bucketStatesToEntities(acc, colState, bucket);
                map.forEach(newOpenSubspace.entitiesWithPaths::put);
                acc.openDevSubspaces.add(newOpenSubspace);
            });
        }

        acc.openDevSubspaces.forEach(subspace ->
                finalizeDeviationSubspace(acc, subspace, Iterables.getLast(collectivePath.get())));

        LOG.info("Subspace calc time = " + ((System.nanoTime() - startTime) / 1000));
        return acc.closedDevSubspaces;
    }

    private void finalizeDeviationSubspace(Accumulator acc, OpenDeviationSubspace openDeviationSubspace, GridCollectiveState currentState) {
        GridCollectiveStateSpace stateSpace = acc.inputPlan.getCollectiveStateSpace();

        Set<GridCollectiveState> stateSet = buildDeviationSubspaceStatesSet(stateSpace, openDeviationSubspace);

        GridCollectiveState furthestContainedCollectiveState = findFurthestStateContainedInDevSubspace(acc,
                currentState, stateSet, openDeviationSubspace);

        GridCollectiveDeviationSubspace newDevSubspace = new GridCollectiveDeviationSubspace(stateSpace, stateSet, furthestContainedCollectiveState);

        acc.closedDevSubspaces.add(newDevSubspace);
    }

    private Set<GridCollectiveState> buildDeviationSubspaceStatesSet(GridCollectiveStateSpace stateSpace, OpenDeviationSubspace devSubspaceToClose) {
        List<List<GridEntityState>> statePaths = devSubspaceToClose.entitiesWithPaths.asMap().entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(ImmutableList::copyOf)
                .collect(Collectors.toList());
        int size = statePaths.get(0).size();

        Set<GridCollectiveState> collectiveStates = new HashSet<>();
        for (int i = 0; i < size; i++) {
            List<GridEntityState> states = extractCollectiveStateAtPosition(statePaths, i);
            GridCollectiveState colState = stateSpace.collectiveStateFrom(states);
            if (colState == null) {
                throw new IllegalStateException("Invalid state");
            }
            collectiveStates.add(colState);
        }

        for (int i = 0; i < expansionFactor; i++) {
            Set<GridCollectiveState> neighborStates = new HashSet<>();
            collectiveStates.forEach(colState -> neighborStates.addAll(stateSpace.getNeighborStatesOf(colState)));
            neighborStates.addAll(collectiveStates);
            collectiveStates = neighborStates;
        }

        return collectiveStates;
    }

    private List<GridEntityState> extractCollectiveStateAtPosition(List<List<GridEntityState>> statePaths, int pos) {
        return statePaths.stream()
                .map(path -> path.get(pos))
                .collect(Collectors.toList());
    }

    private boolean entitySetMatches(OpenDeviationSubspace openSubspace, Set<Object> entities) {
        return openSubspace.entitiesWithPaths.keySet().equals(entities);
    }

    private Map<Object, GridEntityState> bucketStatesToEntities(Accumulator acc, GridCollectiveState colState, BucketOfStates bucket) {
        List<?> entities = acc.inputPlan.getEntities();
        Map<Object, GridEntityState> states = new HashMap<>();
        bucket.states.forEach(bucketedState -> {
            int idx = colState.getEntityStates().indexOf(bucketedState);
            states.put(entities.get(idx), bucketedState);
        });
        return states;
    }

    private Set<BucketOfStates> findBucketsOfProximateStates(Accumulator acc, GridCollectiveState colState) {
        Set<BucketOfStates> buckets = new HashSet<>();
        for (GridEntityState entState : colState.getEntityStates()) {
            buckets.add(new BucketOfStates(entState));
        }
        while (true) {
            Set<BucketOfStates> mergedBuckets = findAndMergeBuckets(acc, buckets);
            if (mergedBuckets.isEmpty()) {
                break;
            }
            mergedBuckets.forEach(mergedBucket -> buckets.removeIf(mergedBucket::contains));
            buckets.addAll(mergedBuckets);
        }
        return buckets;
    }

    private Set<BucketOfStates> findAndMergeBuckets(Accumulator acc, Set<BucketOfStates> openBuckets) {
        Set<BucketOfStates> mergedBuckets = new HashSet<>();

        for (BucketOfStates b1 : openBuckets) {
            for (BucketOfStates b2 : openBuckets) {
                if (b1.equals(b2)) {
                    continue;
                }
                if (canMergeBuckets(b1, b2, acc)) {
                    mergedBuckets.add(new BucketOfStates(b1, b2));
                }
            }
        }
        return mergedBuckets;
    }

    private boolean canMergeBuckets(BucketOfStates bucket, BucketOfStates otherBucket, Accumulator acc) {
        boolean allStatesInProximity = true;
        for (GridEntityState state : bucket.states) {
            for (GridEntityState otherState : otherBucket.states) {
                if (state.equals(otherState))
                    continue;
                if (acc.inputPlan.getDistanceHeuristic().estimateHeuristicDistanceForEntity(state, otherState) > riskProximity) {
                    allStatesInProximity = false;
                    break;
                }
            }
        }
        return allStatesInProximity;
    }

    private GridCollectiveState findFurthestStateContainedInDevSubspace(Accumulator acc, GridCollectiveState currentFullState,
                                                                        Set<GridCollectiveState> states, OpenDeviationSubspace openDeviationSubspace) {
        GridCollectiveState lastContained = currentFullState;
        List<Integer> indexesOfEntities = getIndexesOfEntities(acc, openDeviationSubspace);
        int index = acc.collectivePath.get().indexOf(currentFullState);

        for (int i = index; i < acc.collectivePath.get().size(); i++) {
            GridCollectiveState fullState = acc.collectivePath.get().get(i);
            GridCollectiveState reducedState = acc.inputPlan.getCollectiveStateSpace().reduceState(fullState, indexesOfEntities);
            if (states.contains(reducedState)) {
                lastContained = reducedState;
            }
        }
        return lastContained;
    }

    private List<Integer> getIndexesOfEntities(Accumulator acc, OpenDeviationSubspace openDeviationSubspace) {
        return openDeviationSubspace.entitiesWithPaths.keySet()
                .stream()
                .map(o -> acc.inputPlan.getEntities().indexOf(o))
                .collect(Collectors.toList());
    }

    private class Accumulator {

        private final GridInputPlan inputPlan;
        private final CollectivePath<GridCollectiveState> collectivePath;
        private final Set<OpenDeviationSubspace> openDevSubspaces = new HashSet<>();
        private final List<GridDeviationSubspace> closedDevSubspaces = new LinkedList<>();

        private Accumulator(GridInputPlan inputPlan, CollectivePath<GridCollectiveState> collectivePath) {
            this.inputPlan = inputPlan;
            this.collectivePath = collectivePath;
        }

    }

    private class OpenDeviationSubspace {

        private final LinkedListMultimap<Object, GridEntityState> entitiesWithPaths;

        private OpenDeviationSubspace() {
            entitiesWithPaths = LinkedListMultimap.create();
        }

        @Override
        public String toString() {
            return entitiesWithPaths.toString();
        }
    }

    private class BucketOfStates {
        private Set<GridEntityState> states = new HashSet<>();

        public BucketOfStates(GridEntityState cs) {
            states = new HashSet<>();
            states.add(cs);
        }

        public BucketOfStates(BucketOfStates bucket, BucketOfStates otherBucket) {
            states = new HashSet<>();
            states.addAll(bucket.states);
            states.addAll(otherBucket.states);
        }

        public boolean contains(BucketOfStates other) {
            return states.containsAll(other.states);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BucketOfStates that = (BucketOfStates) o;

            return states.equals(that.states);
        }

        @Override
        public int hashCode() {
            return states.hashCode();
        }

        @Override
        public String toString() {
            return states.toString();
        }
    }

    public GridDeviationSubspaceLocator setRiskProximity(double riskProximity) {
        this.riskProximity = riskProximity;
        return this;
    }

    public GridDeviationSubspaceLocator setExpansionFactor(int expansionFactor) {
        this.expansionFactor = expansionFactor;
        return this;
    }

}
