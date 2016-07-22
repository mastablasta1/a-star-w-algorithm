package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.AbstractNumberHandler;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.InputPlan;
import edu.agh.idziak.astarw.StateSpace;
import edu.agh.idziak.common.Statistics;
import edu.agh.idziak.common.StatisticsSource;
import edu.agh.idziak.common.ValueSortedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
class GlobalAStar<SS extends StateSpace<GS, U>, GS extends GlobalState<U>, U extends Comparable<U>> implements StatisticsSource {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalAStar.class);
    private static final String STATISTICS_STATES_VISITED = "statesVisited";
    private final Statistics statistics = new Statistics("globalAStar");

    private AbstractNumberHandler<U> numHandler;

    GlobalAStar(AbstractNumberHandler<U> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    ImmutablePath<U> calculatePath(PlanningData<SS, GS, U> planningData) {
        Accumulator acc = new Accumulator(planningData.getInputPlan());

        LOG.debug("Starting A*, start={}, end={}, stateSpace:\n{}", acc.start, acc.goal, acc.stateSpace);

        acc.gScore.put(acc.start, numHandler.getZero());
        acc.openSetWithFScore.put(acc.start, acc.stateSpace.getHeuristicDistance(acc.start, acc.goal));

        boolean pathFound = findPath(acc);

        ImmutablePath<U> path = finalizeAlgorithm(pathFound, acc);
        planningData.setPath(path);
        return path;
    }

    private ImmutablePath<U> finalizeAlgorithm(boolean pathFound, Accumulator acc) {
        ImmutablePath<U> path = null;
        if (pathFound)
            path = reconstructPath(acc);

        LOG.info(pathFound ? "Path found: {}" : "Path not found", path);
        LOG.info(statistics.toString());

        return path;
    }

    private boolean findPath(Accumulator acc) {
        while (!acc.openSetWithFScore.isEmpty()) {
            assert statistics.countStat(STATISTICS_STATES_VISITED);

            GS current = acc.openSetWithFScore.pollKeyWithLowestValue();
            LOG.trace("Processing state {}", current);

            if (current.equals(acc.goal)) {
                return true;
            }
            acc.closedSet.add(current);

            iterateNeighbors(acc, current);
        }
        return false;
    }

    private void iterateNeighbors(Accumulator acc, GS current) {
        Set<GS> neighborsOfCurrent = acc.stateSpace.getNeighborStatesOf(current);

        for (GS neighbor : neighborsOfCurrent) {
            if (acc.closedSet.contains(neighbor))
                continue;

            U tentativeGScore = numHandler.add(acc.gScore.get(current), acc.stateSpace.getHeuristicDistance(current, neighbor));

            if (acc.openSetWithFScore.containsKey(neighbor) && numHandler.greaterOrEqual(tentativeGScore, acc.gScore.get(neighbor))) {
                continue;
            }

            acc.cameFrom.put(neighbor, current);
            acc.gScore.put(neighbor, tentativeGScore);
            U fScore = numHandler.add(tentativeGScore, acc.stateSpace.getHeuristicDistance(neighbor, acc.goal));
            acc.openSetWithFScore.put(neighbor, fScore);
        }
    }


    private ImmutablePath<U> reconstructPath(Accumulator acc) {
        List<GS> totalPath = new LinkedList<>();
        GS current = acc.goal;
        totalPath.add(current);

        while ((current = acc.cameFrom.get(current)) != null) {
            totalPath.add(0, current);
        }
        return ImmutablePath.from(totalPath);
    }


    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    private class Accumulator {
        private SS stateSpace;
        private GS start;
        private GS goal;
        private ValueSortedMap<GS, U> openSetWithFScore;
        private Set<GS> closedSet;
        private Map<GS, U> gScore;
        private Map<GS, GS> cameFrom;

        Accumulator(InputPlan<SS, GS, U> inputPlan) {
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
