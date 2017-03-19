package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.common.ValueSortedPriorityQueue;
import pl.edu.agh.idziak.asw.model.*;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class CollectiveAStarImpl<SS extends StateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>> implements CollectiveAStar<SS, CS, D> {

    private AbstractNumberHandler<D> numHandler;
    private static int logCount = 0;
    private static int LOG_EVERY_N_ITERATIONS = 10;

    public CollectiveAStarImpl(AbstractNumberHandler<D> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    public CollectiveAStarResults<CS> calculatePath(InputPlan<SS, CS, D> inputPlan, boolean gatherStats) {
        Accumulator acc = new Accumulator(inputPlan, gatherStats);

        acc.gScore.put(acc.start, numHandler.getZero());
        acc.openSetWithFScore.add(acc.start, acc.costFunction.getHeuristicCostEstimate(acc.start, acc.goal));

        boolean pathFound = findPath(acc);

        CollectivePath<CS> collectivePath = pathFound ? reconstructPath(acc) : null;

        return new CollectiveAStarResults<>(collectivePath, acc.stats);
    }

    @Override
    public CollectivePath<CS> calculatePath(InputPlan<SS, CS, D> inputPlan) {
        return calculatePath(inputPlan, false).getCollectivePath();
    }

    private boolean findPath(Accumulator acc) {
        while (!acc.openSetWithFScore.isEmpty()) {

            if (acc.gatherStats) {
                acc.stats.logSizeOfOpenSet(acc.openSetWithFScore.size());
                if (logCount > LOG_EVERY_N_ITERATIONS) {
                    logCount = 0;
                    System.out.println("Iterations: "+acc.stats.getSizeOfOpenSetLog().size()+", maxSize: "+acc.stats.maxSizeOfOpenSet());
                }
                logCount++;
            }

            acc.openSetWithFScore.pollFirst();
            CS current = acc.openSetWithFScore.getFirstKey();

            if (current.equals(acc.goal)) {
                return true;
            }

            acc.closedSet.add(current);

            iterateNeighbors(acc, current);
        }
        return false;
    }

    private void iterateNeighbors(Accumulator acc, CS current) {
        Collection<CS> neighborsOfCurrent = acc.stateSpace.getNeighborStatesOf(current);

        for (CS neighbor : neighborsOfCurrent) {
            if (acc.closedSet.contains(neighbor))
                continue;

            D tentativeGScore = numHandler
                    .add(acc.gScore.get(current), acc.costFunction.getDistanceBetween(current, neighbor));

            if (acc.openSetWithFScore.containsKey(neighbor)
                    && numHandler.greaterOrEqual(tentativeGScore, acc.gScore.get(neighbor))) {
                continue;
            }

            acc.cameFrom.put(neighbor, current);
            acc.gScore.put(neighbor, tentativeGScore);
            D heuristicDistNeighborToGoal = acc.costFunction.getHeuristicCostEstimate(neighbor, acc.goal);
            D fScore = numHandler.add(tentativeGScore, heuristicDistNeighborToGoal);
            acc.openSetWithFScore.add(neighbor, fScore);
        }
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

    private class Accumulator {

        private SS stateSpace;
        private CS start;
        private CS goal;
        private ValueSortedPriorityQueue<CS, D> openSetWithFScore;
        private Set<CS> closedSet;
        private Map<CS, D> gScore;
        private Map<CS, CS> cameFrom;
        private CostFunction<CS, D> costFunction;
        private CollectiveAStarStats stats;
        private boolean gatherStats;

        Accumulator(InputPlan<SS, CS, D> inputPlan, boolean gatherStats) {
            stateSpace = inputPlan.getStateSpace();
            start = inputPlan.getInitialCollectiveState();
            goal = inputPlan.getTargetCollectiveState();
            costFunction = inputPlan.getCostFunction();

            openSetWithFScore = new ValueSortedPriorityQueue<>();
            closedSet = new HashSet<>();
            gScore = new HashMap<>();
            cameFrom = new HashMap<>();
            stats = new CollectiveAStarStats();
            this.gatherStats = gatherStats;
        }
    }


}
