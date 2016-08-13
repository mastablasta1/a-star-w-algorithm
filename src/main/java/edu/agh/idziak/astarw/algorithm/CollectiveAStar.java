package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;
import edu.agh.idziak.common.BasicValueSortedMap;
import edu.agh.idziak.common.Pair;
import edu.agh.idziak.common.Statistics;
import edu.agh.idziak.common.StatisticsSource;
import edu.agh.idziak.common.ValueSortedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
class CollectiveAStar<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> implements StatisticsSource {
    private static final Logger LOG = LoggerFactory.getLogger(CollectiveAStar.class);
    private static final String STATISTICS_STATES_VISITED = "statesVisited";
    private static final String STATISTICS_MAX_SIZE_OF_OPENSET = "maxSizeOfOpenSet";
    private final Statistics statistics = new Statistics("collectiveAStar");

    private AbstractNumberHandler<D> numHandler;

    CollectiveAStar(AbstractNumberHandler<D> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    ImmutableCollectivePath<P> calculatePath(PlanningData<SS, GS, P, D> planningData) {
        Accumulator acc = new Accumulator(planningData.getInputPlan());

        LOG.debug("Starting A*, start={}, end={}, stateSpace:\n{}", acc.start, acc.goal, acc.stateSpace);

        acc.gScore.put(acc.start, numHandler.getZero());
        acc.openSetWithFScore.put(acc.start, acc.stateSpace.getHeuristicDistance(acc.start, acc.goal));

        boolean pathFound = findPath(acc);

        ImmutableCollectivePath<P> path = finalizeCalculation(pathFound, acc);
        planningData.setCollectivePath(path);
        return path;
    }

    private ImmutableCollectivePath<P> finalizeCalculation(boolean pathFound, Accumulator acc) {
        ImmutableCollectivePath<P> path = null;
        if (pathFound) {
            path = reconstructPath(acc);
            LOG.info("Path of length {} found: {}", path.get().size(), path);
        } else {
            LOG.info("Path not found");
        }
        LOG.info(statistics.toString());

        return path;
    }

    private boolean findPath(Accumulator acc) {
        while (!acc.openSetWithFScore.isEmpty()) {
            assert updateStats(acc);

            Pair<GS, D> currentPair = acc.openSetWithFScore.pollEntryWithLowestValue();
            GS current = currentPair.getOne();
            LOG.debug("Entering state {}, FScore={}", currentPair.getOne(), currentPair.getTwo());

            if (current.equals(acc.goal)) {
                return true;
            }
            acc.closedSet.add(current);

            iterateNeighbors(acc, current);
        }
        return false;
    }

    private boolean updateStats(Accumulator acc) {
        statistics.countStat(STATISTICS_STATES_VISITED);
        statistics.maxStat(STATISTICS_MAX_SIZE_OF_OPENSET, acc.openSetWithFScore.size());
        return true;
    }

    private void iterateNeighbors(Accumulator acc, GS current) {
        Set<GS> neighborsOfCurrent = acc.stateSpace.getNeighborStatesOf(current);

        for (GS neighbor : neighborsOfCurrent) {
            if (acc.closedSet.contains(neighbor))
                continue;

            D tentativeGScore = numHandler.add(acc.gScore.get(current), acc.stateSpace.getHeuristicDistance(current, neighbor));

            if (acc.openSetWithFScore.containsKey(neighbor)
                    && numHandler.greaterOrEqual(tentativeGScore, acc.gScore.get(neighbor))) {
                continue;
            }

            acc.cameFrom.put(neighbor, current);
            acc.gScore.put(neighbor, tentativeGScore);
            D heuristicDistNeighborToGoal = acc.stateSpace.getHeuristicDistance(neighbor, acc.goal);
            D fScore = numHandler.add(tentativeGScore, heuristicDistNeighborToGoal);
            acc.openSetWithFScore.put(neighbor, fScore);
        }
    }

    private ImmutableCollectivePath<P> reconstructPath(Accumulator acc) {
        List<GS> totalPath = new LinkedList<>();
        GS current = acc.goal;
        totalPath.add(current);

        while ((current = acc.cameFrom.get(current)) != null) {
            totalPath.add(0, current);
        }
        return ImmutableCollectivePath.from(totalPath);
    }


    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    private class Accumulator {
        private SS stateSpace;
        private GS start;
        private GS goal;
        private ValueSortedMap<GS, D> openSetWithFScore;
        private Set<GS> closedSet;
        private Map<GS, D> gScore;
        private Map<GS, GS> cameFrom;

        Accumulator(InputPlan<SS, GS, P, D> inputPlan) {
            stateSpace = inputPlan.getStateSpace();
            start = inputPlan.getInitialGlobalState();
            goal = inputPlan.getTargetGlobalState();

            openSetWithFScore = new BasicValueSortedMap<>();
            closedSet = new HashSet<>();
            gScore = new HashMap<>();
            cameFrom = new HashMap<>();
        }
    }


}
