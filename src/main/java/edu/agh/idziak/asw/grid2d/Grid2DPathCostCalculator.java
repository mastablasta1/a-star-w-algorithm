package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.CollectivePath;
import edu.agh.idziak.common.WalkingPairIterator;

import java.util.List;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class Grid2DPathCostCalculator {

    public static double calculateCost(CollectivePath<Integer> path, Grid2DStateSpace stateSpace) {
        List<Grid2DCollectiveState> states = path.get(Grid2DCollectiveState.class);

        WalkingPairIterator<Grid2DCollectiveState> it = new WalkingPairIterator<>(states);

        double cost = 0.0;

        while (it.hasNext()) {
            it.next();

            cost += stateSpace.getHeuristicDistance(it.getFirst(), it.getSecond());
        }
        return cost;
    }
}
