package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;
import edu.agh.idziak.common.ValueSortedMap;
import edu.agh.idziak.common.Pair;
import edu.agh.idziak.common.Statistics;
import edu.agh.idziak.common.StatisticsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
class CollectiveAStar<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> implements StatisticsSource {
    private static final Logger LOG = LoggerFactory.getLogger(CollectiveAStar.class);
    private static final String STATISTICS_STATES_VISITED = "statesVisited";
    private static final String STATISTICS_MAX_SIZE_OF_OPENSET = "maxSizeOfOpenSet";
    private final Statistics statistics = new Statistics("collectiveAStar");

    private AbstractNumberHandler<D> numHandler;

    CollectiveAStar(AbstractNumberHandler<D> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    ImmutableCollectivePath<CS, ES, P> calculatePath(PlanningData<SS, CS, ES, P, D> planningData) {
        Accumulator acc = new Accumulator(planningData.getInputPlan());

        LOG.debug("Starting A*, start={}, end={}, stateSpace:\n{}", acc.start, acc.goal, acc.stateSpace);

        acc.gScore.put(acc.start, numHandler.getZero());
        acc.openSetWithFScore.put(acc.start, acc.stateSpace.getHeuristicCost(acc.start, acc.goal));

        boolean pathFound = findPath(acc);

        ImmutableCollectivePath<CS, ES, P> path = finalizeCalculation(pathFound, acc);
        planningData.setCollectivePath(path);
        return path;
    }

    private ImmutableCollectivePath<CS, ES, P> finalizeCalculation(boolean pathFound, Accumulator acc) {
        ImmutableCollectivePath<CS, ES, P> path = null;
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

            Pair<CS, D> currentPair = acc.openSetWithFScore.pollEntryWithLowestValue();
            CS current = currentPair.getOne();
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

    private void iterateNeighbors(Accumulator acc, CS current) {
        Set<CS> neighborsOfCurrent = acc.stateSpace.getNeighborStatesOf(current);

        for (CS neighbor : neighborsOfCurrent) {
            if (acc.closedSet.contains(neighbor))
                continue;

            D tentativeGScore = numHandler.add(acc.gScore.get(current), acc.stateSpace.getHeuristicCost(current, neighbor));

            if (acc.openSetWithFScore.containsKey(neighbor)
                    && numHandler.greaterOrEqual(tentativeGScore, acc.gScore.get(neighbor))) {
                continue;
            }

            acc.cameFrom.put(neighbor, current);
            acc.gScore.put(neighbor, tentativeGScore);
            D heuristicDistNeighborToGoal = acc.stateSpace.getHeuristicCost(neighbor, acc.goal);
            D fScore = numHandler.add(tentativeGScore, heuristicDistNeighborToGoal);
            acc.openSetWithFScore.put(neighbor, fScore);
        }
    }

    private ImmutableCollectivePath<CS, ES, P> reconstructPath(Accumulator acc) {
        List<CS> totalPath = new LinkedList<>();
        CS current = acc.goal;
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
        private CS start;
        private CS goal;
        private ValueSortedMap<CS, D> openSetWithFScore;
        private Set<CS> closedSet;
        private Map<CS, D> gScore;
        private Map<CS, CS> cameFrom;

        Accumulator(InputPlan<SS, CS, ES, P, D> inputPlan) {
            stateSpace = inputPlan.getStateSpace();
            start = inputPlan.getInitialGlobalState();
            goal = inputPlan.getTargetGlobalState();

            openSetWithFScore = new ValueSortedMap<>();
            closedSet = new HashSet<>();
            gScore = new HashMap<>();
            cameFrom = new HashMap<>();
        }
    }


}
