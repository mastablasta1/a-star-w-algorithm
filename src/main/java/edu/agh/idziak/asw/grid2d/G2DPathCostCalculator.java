package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.CollectivePath;
import edu.agh.idziak.common.WalkingPairIterator;

import java.util.List;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class G2DPathCostCalculator {

    public static double calculateCost(CollectivePath<G2DEntityState, Integer> path, G2DStateSpace stateSpace) {
        List<G2DCollectiveState> states = path.get(G2DCollectiveState.class);

        WalkingPairIterator<G2DCollectiveState> it = new WalkingPairIterator<>(states);

        double cost = 0.0;

        while (it.hasNext()) {
            it.next();

            cost += stateSpace.getHeuristicDistance(it.getFirst(), it.getSecond());
        }
        return cost;
    }
}
