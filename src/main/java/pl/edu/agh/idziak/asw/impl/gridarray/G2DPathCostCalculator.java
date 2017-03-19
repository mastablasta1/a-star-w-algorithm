package pl.edu.agh.idziak.asw.impl.gridarray;

import pl.edu.agh.idziak.asw.common.WalkingPairIterator;
import pl.edu.agh.idziak.asw.model.CollectivePath;

import java.util.List;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class G2DPathCostCalculator {

    public static double calculateCost(CollectivePath<G2DOptCollectiveState> path, G2DCostFunction costFunction) {
        List<G2DOptCollectiveState> states = path.get();
        G2DOptCollectiveState goal = states.get(states.size() - 1);
        WalkingPairIterator<G2DOptCollectiveState> it = new WalkingPairIterator<>(states);

        double cost = 0.0;

        while (it.hasNext()) {
            it.next();
            cost += costFunction.getHeuristicCostEstimate(it.getFirst(), it.getSecond());
        }
        return cost;
    }
}
