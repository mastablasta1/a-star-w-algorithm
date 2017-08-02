package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.DeviationSubspaceLocator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 13.03.2017.
 */
public class GridDeviationSubspaceLocator implements DeviationSubspaceLocator<GridInputPlan, GridCollectiveState> {
    private static final int WARNING_PROXIMITY = 2;
    private static final int EXPANSION_FACTOR = 1;

    @Override
    public Set<GridDeviationSubspace> findDeviationSubspaces(GridInputPlan inputPlan, CollectivePath<GridCollectiveState> collectivePath) {
        Accumulator acc = new Accumulator(inputPlan, collectivePath);

        for (GridCollectiveState currentCollectiveState : collectivePath.get()) {
            Set<BucketOfStates> bucketsOfProximateStates = buildBucketsOfProximateStates(currentCollectiveState, acc);

            Iterator<OpenDeviationSubspace> openDevSubspacesIterator = acc.openDevSubspaces.iterator();

            while (openDevSubspacesIterator.hasNext()) {
                OpenDeviationSubspace currentDevSubspace = openDevSubspacesIterator.next();
                Set<Integer> entityIndexes = getIndexesOfEntitiesInDevSubspace(acc.inputPlan, currentDevSubspace);
                BucketOfStates matchingBucket = findMatchingBucket(bucketsOfProximateStates, entityIndexes);

                if (matchingBucket != null) {
                    matchingBucket.entityStates.forEach((index, state) -> currentDevSubspace.containedStates.add(state));
                    bucketsOfProximateStates.remove(matchingBucket);
                } else {
                    finalizeDeviationSubspace(acc, currentDevSubspace, currentCollectiveState);
                    openDevSubspacesIterator.remove();
                }
            }

            openNewDevSubspacesForBuckets(acc, bucketsOfProximateStates);
        }

        acc.openDevSubspaces.forEach(openDeviationSubspace ->
                finalizeDeviationSubspace(acc, openDeviationSubspace, Iterables.getLast(collectivePath.get())));
        return ImmutableSet.copyOf(acc.outputDeviationSubspaces);
    }

    private void openNewDevSubspacesForBuckets(Accumulator acc, Set<BucketOfStates> bucketsOfProximateStates) {
        bucketsOfProximateStates.forEach(newBucket -> {
            HashSet<GridEntityState> initialStateSet = new HashSet<>(newBucket.entityStates.values());
            OpenDeviationSubspace newDevSubspaceBuilder = new OpenDeviationSubspace(newBucket.entities, initialStateSet);
            acc.openDevSubspaces.add(newDevSubspaceBuilder);
        });
    }

    private Set<Integer> getIndexesOfEntitiesInDevSubspace(GridInputPlan inputPlan, OpenDeviationSubspace openDevSubspace) {
        return openDevSubspace.entities.stream()
                .map(entity -> inputPlan.getEntities().indexOf(entity))
                .collect(Collectors.toSet());
    }

    private void finalizeDeviationSubspace(Accumulator acc, OpenDeviationSubspace openDeviationSubspace, GridCollectiveState currentState) {
        GridStateSpace stateSpace = acc.inputPlan.getStateSpace();

        expandDevSubspace(stateSpace, openDeviationSubspace);

        GridCollectiveState furthestContainedCollectiveState = findFurthestStateContainedInDevSubspace(acc, currentState, openDeviationSubspace);

        GridNonCollectiveDeviationSubspace newDevSubspace = new GridNonCollectiveDeviationSubspace(
                openDeviationSubspace.containedStates,
                acc.inputPlan.getStateSpace(),
                furthestContainedCollectiveState.getEntityStates(),
                openDeviationSubspace.entities);

        acc.outputDeviationSubspaces.add(newDevSubspace);
    }

    private void expandDevSubspace(GridStateSpace stateSpace, OpenDeviationSubspace devSubspaceToClose) {
        for (int i = 0; i < EXPANSION_FACTOR; i++) {
            Set<GridEntityState> tempSet = new HashSet<>(devSubspaceToClose.containedStates);
            for (GridEntityState entityState : tempSet) {
                devSubspaceToClose.containedStates.addAll(stateSpace.getNeighborStatesOf(entityState));
            }
        }
    }

    private GridCollectiveState findFurthestStateContainedInDevSubspace(Accumulator acc, GridCollectiveState currentState, OpenDeviationSubspace devSubspaceBuilder) {
        int lastCollectiveStateIndex = acc.collectivePath.get().indexOf(currentState);
        int pathSize = acc.collectivePath.get().size();

        GridCollectiveState lastContainedCollectiveState = currentState;
        for (int i = lastCollectiveStateIndex; i < pathSize; i++) {
            GridCollectiveState collectiveState = acc.collectivePath.get().get(i);
            if (!devSubspaceBuilder.containedStates.containsAll(collectiveState.getEntityStates())) {
                break;
            }
            lastContainedCollectiveState = collectiveState;
        }
        return lastContainedCollectiveState;
    }

    private static BucketOfStates findMatchingBucket(Set<BucketOfStates> bucketsOfStates, Set<Integer> entityIndexes) {
        for (BucketOfStates bucket : bucketsOfStates) {
            if (entityIndexes.size() == bucket.entityStates.size()
                    && entityIndexes.containsAll(bucket.entityStates.keySet())) {
                return bucket;
            }
        }
        return null;
    }


    private Set<BucketOfStates> buildBucketsOfProximateStates(GridCollectiveState collectiveState, Accumulator acc) {
        Set<BucketOfStates> bucketsOfProximateStates = new HashSet<>();

        List<GridEntityState> entityStates = collectiveState.getEntityStates();

        for (int entityIndex = 0; entityIndex < entityStates.size(); entityIndex++) {
            GridEntityState entityState = entityStates.get(entityIndex);
            BucketOfStates existingBucketToExpand = findBucketToExpand(bucketsOfProximateStates, entityState);

            if (existingBucketToExpand != null) {
                existingBucketToExpand.entityStates.put(entityIndex, entityState);
            } else {
                BucketOfStates newBucket = new BucketOfStates();
                newBucket.entityStates.put(entityIndex, entityState);
                newBucket.entities.add(acc.inputPlan.getEntities().get(entityIndex));
                bucketsOfProximateStates.add(newBucket);
            }
        }
        return bucketsOfProximateStates;
    }

    private BucketOfStates findBucketToExpand(Set<BucketOfStates> bucketsOfProximateStates, GridEntityState entityState) {
        BucketOfStates existingBucketToExpand = null;
        outsideLoop:
        for (BucketOfStates bucket : bucketsOfProximateStates) {
            for (Map.Entry<Integer, GridEntityState> bucketedState : bucket.entityStates.entrySet()) {
                if (areStatesProximate(entityState, bucketedState.getValue())) {
                    existingBucketToExpand = bucket;
                    break outsideLoop;
                }
            }
        }
        return existingBucketToExpand;
    }

    private boolean areStatesProximate(GridEntityState state1, GridEntityState state2) {
        int manhattanDistance = Math.abs(state1.getCol() - state2.getCol())
                + Math.abs(state1.getRow() - state2.getRow());
        return manhattanDistance <= WARNING_PROXIMITY;
    }

    private class Accumulator {

        private final GridInputPlan inputPlan;
        private final CollectivePath<GridCollectiveState> collectivePath;
        private final Set<OpenDeviationSubspace> openDevSubspaces = new HashSet<>();
        private final List<GridDeviationSubspace> outputDeviationSubspaces = new LinkedList<>();

        private Accumulator(GridInputPlan inputPlan, CollectivePath<GridCollectiveState> collectivePath) {
            this.inputPlan = inputPlan;
            this.collectivePath = collectivePath;
        }

    }

    private class OpenDeviationSubspace {

        private final List<Object> entities;
        private final Set<GridEntityState> containedStates;

        private OpenDeviationSubspace(List<Object> entities, HashSet<GridEntityState> initialStateSet) {
            this.entities = ImmutableList.copyOf(entities);
            this.containedStates = initialStateSet;
        }
    }

    private static class BucketOfStates {
        private final Map<Integer, GridEntityState> entityStates = new HashMap<>();
        private final List<Object> entities = new LinkedList<>();
    }
}
