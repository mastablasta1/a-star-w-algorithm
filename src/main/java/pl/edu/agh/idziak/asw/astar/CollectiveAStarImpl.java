package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.*;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class CollectiveAStarImpl<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>> implements CollectiveAStar<SS, CS, D> {

    private AbstractNumberHandler<D> numHandler;
    private AStarStateMonitor<CS> aStarStateMonitor;
    private SortingPreference sortingPreference;

    public CollectiveAStarImpl(AbstractNumberHandler<D> abstractNumberHandler) {
        this(abstractNumberHandler, SortingPreference.NONE);
    }

    public CollectiveAStarImpl(AbstractNumberHandler<D> abstractNumberHandler, SortingPreference sortingPreference) {
        this.numHandler = abstractNumberHandler;
        this.sortingPreference = checkNotNull(sortingPreference);
    }

    public CollectivePath<CS> calculatePath(InputPlan<SS, CS, D> inputPlan) {
        Accumulator acc = new Accumulator(inputPlan);

        acc.gScore.put(acc.start, numHandler.getZero());
        D heuristicDistance = acc.distanceHeuristic.estimateHeuristicDistance(acc.start, acc.goal);
        acc.openSetWithFScore.add(acc.start, heuristicDistance, numHandler.getZero());

        boolean pathFound = findPath(acc);

        if (aStarStateMonitor != null) {
            aStarStateMonitor.onSuccess(acc.closedSet.size(), acc.openSetWithFScore.size());
        }

        return pathFound ? reconstructPath(acc) : null;
    }

    private boolean findPath(Accumulator acc) {
        while (!acc.openSetWithFScore.isEmpty()) {
            DoubleValuePriorityMap.Entry<CS, D> currentBest = acc.openSetWithFScore.pollFirstKey();

            if (currentBest.getKey().equals(acc.goal)) {
                return true;
            }

            acc.closedSet.add(currentBest.getKey());

            iterateNeighbors(acc, currentBest);
        }
        return false;
    }

    private void iterateNeighbors(Accumulator acc, DoubleValuePriorityMap.Entry<CS, D> currentBest) {
        Collection<CS> neighborsOfCurrent = acc.stateSpace.getNeighborStatesOf(currentBest.getKey());
        CS current = currentBest.getKey();

        if (aStarStateMonitor != null) {
            executeIterationMonitorCallback(acc, currentBest, neighborsOfCurrent);
        }

        for (CS neighbor : neighborsOfCurrent) {
            if (acc.closedSet.contains(neighbor))
                continue;

            D distToNeighbor = acc.distanceHeuristic.getDistanceBetween(current, neighbor);
            D gScore = numHandler.add(acc.gScore.get(current), distToNeighbor);

            if (acc.openSetWithFScore.containsKey(neighbor)
                    && numHandler.greaterOrEqual(gScore, acc.gScore.get(neighbor))) {
                continue;
            }

            acc.cameFrom.put(neighbor, current);
            acc.gScore.put(neighbor, gScore);
            D hScore = acc.distanceHeuristic.estimateHeuristicDistance(neighbor, acc.goal);
            D fScore = numHandler.add(gScore, hScore);
            acc.openSetWithFScore.add(neighbor, fScore, gScore);
        }
    }

    private void executeIterationMonitorCallback(Accumulator acc, DoubleValuePriorityMap.Entry<CS, ?> current, Collection<CS> neighborsOfCurrent) {
        aStarStateMonitor.onIteration(new AStarIterationData<>(acc.inputPlan,
                current,
                Collections.unmodifiableCollection(neighborsOfCurrent),
                acc.openSetWithFScore.size(),
                acc.closedSet.size()));
    }

    private CollectivePath<CS> reconstructPath(Accumulator acc) {
        List<CS> reconstructedPath = new LinkedList<>();
        CS current = acc.goal;
        reconstructedPath.add(current);

        while ((current = acc.cameFrom.get(current)) != null) {
            reconstructedPath.add(0, current);
        }
        return ImmutableCollectivePath.from(reconstructedPath);
    }

    public void setaStarStateMonitor(AStarStateMonitor<CS> aStarStateMonitor) {
        this.aStarStateMonitor = aStarStateMonitor;
    }

    private class Accumulator {

        private final SS stateSpace;
        private final CS start;
        private final CS goal;
        private final DoubleValuePriorityMap<CS, D> openSetWithFScore;
        private final Set<CS> closedSet;
        private final Map<CS, D> gScore;
        private final Map<CS, CS> cameFrom;
        private final DistanceHeuristic<CS, D> distanceHeuristic;
        private final InputPlan<SS, CS, D> inputPlan;

        Accumulator(InputPlan<SS, CS, D> inputPlan) {
            this.inputPlan = inputPlan;
            stateSpace = inputPlan.getStateSpace();
            start = inputPlan.getInitialCollectiveState();
            goal = inputPlan.getTargetCollectiveState();
            distanceHeuristic = inputPlan.getDistanceHeuristic();

            if (sortingPreference == SortingPreference.NONE) {
                openSetWithFScore = new SingleValuePriorityMap<>();
            } else if (sortingPreference == SortingPreference.PREFER_HIGHER_G_SCORE) {
                openSetWithFScore = new DoubleValuePriorityMapImpl<>(true);
            } else {
                openSetWithFScore = new DoubleValuePriorityMapImpl<>(false);
            }
            closedSet = new HashSet<>();
            gScore = new HashMap<>();
            cameFrom = new HashMap<>();
        }
    }


}
