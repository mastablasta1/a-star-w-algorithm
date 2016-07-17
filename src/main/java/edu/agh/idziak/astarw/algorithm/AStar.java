package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;
import edu.agh.idziak.common.ValueSortedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
class AStar<SS extends StateSpace<U>, S extends GlobalState<U>, U extends Comparable<U>> {
    private static final Logger LOG = LoggerFactory.getLogger(AStar.class);

    private AbstractNumberHandler<U> numHandler;

    AStar(AbstractNumberHandler<U> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    ImmutableListPath<U> calculatePath(EntityState<U> start, EntityState<U> goal, StateSpace<U> stateSpace) {
        ValueSortedMap<EntityState<U>, U> openSetWithFScore = new ValueSortedMap<>();
        Set<EntityState<U>> closedSet = new HashSet<>();
        Map<EntityState<U>, U> gScore = new HashMap<>();
        Map<EntityState<U>, EntityState<U>> cameFrom = new HashMap<>();

        gScore.put(start, numHandler.getZero());
        openSetWithFScore.put(start, stateSpace.getHeuristicDistance(start, goal));

        LOG.trace("Starting A*, start={}, end={}, stateSpace:\n{}", start, goal, stateSpace);

        while (!openSetWithFScore.isEmpty()) {
            EntityState<U> current = openSetWithFScore.pollKeyWithLowestValue();
            LOG.trace("Processing state {}", current);
            if (current.equals(goal)) {
                LOG.trace("Goal reached");
                return reconstructPath(cameFrom, current);
            }
            closedSet.add(current);

            Set<EntityState<U>> neighborsOfCurrent = stateSpace.getNeighborStatesOf(current);

            for (EntityState<U> neighbor : neighborsOfCurrent) {
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


    private ImmutableListPath<U> reconstructPath(Map<EntityState<U>, EntityState<U>> cameFrom, EntityState<U> current) {
        List<EntityState<U>> totalPath = new LinkedList<>();
        totalPath.add(current);

        while ((current = cameFrom.get(current)) != null) {
            totalPath.add(0, current);
        }
        return ImmutableListPath.from(totalPath);
    }

}
