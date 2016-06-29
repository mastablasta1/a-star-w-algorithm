package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
class AStar<SS extends GlobalStateSpace<U>, S extends GlobalState<U>, U extends Comparable<U>> {

    private AbstractNumberHandler<U> numHandler;

    AStar(AbstractNumberHandler<U> abstractNumberHandler) {
        this.numHandler = abstractNumberHandler;
    }

    ImmutableListPath<U> calculatePath(EntityState<U> start, EntityState<U> goal, PlanningOperation<SS, S, U> planningOperation) {
        InputPlan<SS, S, U> inputPlan = planningOperation.getInputPlan();
        GlobalStateSpace<U> stateSpace = inputPlan.getStateSpace();

        Map<EntityState<U>, U> openSetWithFScore = makeOpenSetWithFScores();
        Set<EntityState<U>> closedSet = new HashSet<>();
        Map<EntityState<U>, U> gScore = new HashMap<>();
        Map<EntityState<U>, EntityState<U>> cameFrom = new HashMap<>();

        gScore.put(start, numHandler.getZero());
        openSetWithFScore.put(start, stateSpace.getHeuristicDistance(start, goal));

        while (!openSetWithFScore.isEmpty()) {
            Map.Entry<EntityState<U>, U> bestBetEntry = openSetWithFScore.entrySet().iterator().next();
            EntityState<U> current = bestBetEntry.getKey();
            if (current == goal) {
                return reconstructPath(cameFrom, current);
            }
            openSetWithFScore.remove(current);
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

        return null;
    }


    private Map<EntityState<U>, U> makeOpenSetWithFScores() {
        FScoreComparator<EntityState<U>, U> FScoreComparator = new FScoreComparator<>();
        Map<EntityState<U>, U> fScoreMap = new TreeMap<>(FScoreComparator);
        FScoreComparator.fScore = fScoreMap;
        return fScoreMap;
    }

    private ImmutableListPath<U> reconstructPath(Map<EntityState<U>, EntityState<U>> cameFrom, EntityState<U> current) {
        List<EntityState<U>> totalPath = new LinkedList<>();
        totalPath.add(current);

        while ((current = cameFrom.get(current)) != null) {
            totalPath.add(current);
        }
        return ImmutableListPath.from(totalPath);
    }


    private class FScoreComparator<K, V extends Comparable<V>> implements Comparator<K> {
        Map<K, V> fScore;

        @Override
        public int compare(K o1, K o2) {
            return fScore.get(o1).compareTo(fScore.get(o2));
        }
    }
}
