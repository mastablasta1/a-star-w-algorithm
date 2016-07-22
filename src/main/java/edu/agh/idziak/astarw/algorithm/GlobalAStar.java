package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.AbstractNumberHandler;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.InputPlan;
import edu.agh.idziak.astarw.StateSpace;
import edu.agh.idziak.common.ValueSortedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
class GlobalAStar<SS extends StateSpace<U>, GS extends GlobalState<U>, U extends Comparable<U>> {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalAStar.class);

    private AbstractNumberHandler<U> numHandler;

    GlobalAStar(AbstractNumberHandler<U> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    @SuppressWarnings("Duplicates")
    public ImmutablePath<U> calculatePath(PlanningData<SS, GS, U> planningData) {
        InputPlan<SS, GS, U> inputPlan = planningData.getInputPlan();
        SS stateSpace = inputPlan.getStateSpace();
        GS start = inputPlan.getInitialGlobalState();
        GS goal = inputPlan.getTargetGlobalState();

        ValueSortedMap<GlobalState<U>, U> openSetWithFScore = new ValueSortedMap<>();
        Set<GlobalState<U>> closedSet = new HashSet<>();
        Map<GlobalState<U>, U> gScore = new HashMap<>();
        Map<GlobalState<U>, GlobalState<U>> cameFrom = new HashMap<>();

        gScore.put(start, numHandler.getZero());
        openSetWithFScore.put(start, stateSpace.getHeuristicDistance(start, goal));

        LOG.trace("Starting A*, start={}, end={}, stateSpace:\n{}", start, goal, stateSpace);

        while (!openSetWithFScore.isEmpty()) {
            GlobalState<U> current = openSetWithFScore.pollKeyWithLowestValue();
            LOG.trace("Processing state {}", current);
            if (current.equals(goal)) {
                LOG.trace("Goal reached");
                ImmutablePath<U> resultPath = reconstructPath(cameFrom, current);
                planningData.setPath(resultPath);
                return resultPath;
            }
            closedSet.add(current);

            Set<GlobalState<U>> neighborsOfCurrent = stateSpace.getNeighborStatesOf(current);

            for (GlobalState<U> neighbor : neighborsOfCurrent) {
                if (closedSet.contains(neighbor))
                    continue;

                U tentativeGScore = numHandler.add(gScore.get(current), stateSpace.getHeuristicDistance(current, neighbor));

                if (openSetWithFScore.containsKey(neighbor) && numHandler.greaterOrEqual(tentativeGScore, gScore.get(neighbor))) {
                    continue;
                }

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                U fScore = numHandler.add(tentativeGScore, stateSpace.getHeuristicDistance(neighbor, goal));
                openSetWithFScore.put(neighbor, fScore);
            }
        }

        LOG.trace("Goal not reached");
        return null;
    }


    private ImmutablePath<U> reconstructPath(Map<GlobalState<U>, GlobalState<U>> cameFrom, GlobalState<U> current) {
        List<GlobalState<U>> totalPath = new LinkedList<>();
        totalPath.add(current);

        while ((current = cameFrom.get(current)) != null) {
            totalPath.add(0, current);
        }
        return ImmutablePath.from(totalPath);
    }

}
