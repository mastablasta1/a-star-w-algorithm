package pl.edu.agh.idziak.asw.astar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.common.Pair;
import pl.edu.agh.idziak.asw.common.Statistics;
import pl.edu.agh.idziak.asw.common.StatisticsSource;
import pl.edu.agh.idziak.asw.common.ValueSortedMap;
import pl.edu.agh.idziak.asw.model.*;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class CollectiveAStar<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>> implements StatisticsSource {
    private static final Logger LOG = LoggerFactory.getLogger(CollectiveAStar.class);
    private static final String STATISTICS_STATES_VISITED = "aStarIterations";
    private static final String STATISTICS_MAX_SIZE_OF_OPENSET = "maxSizeOfOpenSet";
    private Statistics statistics;

    private AbstractNumberHandler<D> numHandler;

    public CollectiveAStar(AbstractNumberHandler<D> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    public CollectivePath<CS> calculatePath(InputPlan<SS, CS, D> inputPlan) {
        statistics = new Statistics("collectiveAStar");
        Accumulator acc = new Accumulator(inputPlan);

        LOG.debug("Starting A*, start={}, end={}, stateSpace:\n{}", acc.start, acc.goal, acc.stateSpace);

        acc.gScore.put(acc.start, numHandler.getZero());
        acc.openSetWithFScore.put(acc.start, acc.costFunction.getHeuristicCost(acc.start, acc.goal));

        findPath(acc);

        return reconstructPath(acc);
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

            D tentativeGScore = numHandler.add(acc.gScore.get(current), acc.costFunction.getHeuristicCost(current,
                    neighbor));

            if (acc.openSetWithFScore.containsKey(neighbor)
                    && numHandler.greaterOrEqual(tentativeGScore, acc.gScore.get(neighbor))) {
                continue;
            }

            acc.cameFrom.put(neighbor, current);
            acc.gScore.put(neighbor, tentativeGScore);
            D heuristicDistNeighborToGoal = acc.costFunction.getHeuristicCost(neighbor, acc.goal);
            D fScore = numHandler.add(tentativeGScore, heuristicDistNeighborToGoal);
            acc.openSetWithFScore.put(neighbor, fScore);
        }
    }

    private CollectivePath<CS> reconstructPath(Accumulator acc) {
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
        private CostFunction<CS, D> costFunction;

        Accumulator(InputPlan<SS, CS, D> inputPlan) {
            stateSpace = inputPlan.getStateSpace();
            start = inputPlan.getInitialCollectiveState();
            goal = inputPlan.getTargetCollectiveState();
            costFunction = inputPlan.getCostFunction();

            openSetWithFScore = new ValueSortedMap<>();
            closedSet = new HashSet<>();
            gScore = new HashMap<>();
            cameFrom = new HashMap<>();
        }
    }


}
