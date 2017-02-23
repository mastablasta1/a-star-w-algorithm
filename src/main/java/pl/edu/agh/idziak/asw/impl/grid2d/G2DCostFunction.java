package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.UntypedTwoMapsIterator;
import pl.edu.agh.idziak.asw.model.CostFunction;

import java.util.Map;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class G2DCostFunction implements CostFunction<G2DCollectiveState, Double> {

    @Override
    public Double getHeuristicCost(G2DCollectiveState start, G2DCollectiveState end) {
        Map<?, G2DEntityState> startStates = start.getEntityStates();
        Map<?, G2DEntityState> endStates = end.getEntityStates();

        UntypedTwoMapsIterator<G2DEntityState> it = new UntypedTwoMapsIterator<>(startStates, endStates);

        double sum = 0;

        while (it.hasNext()) {
            it.next();
            sum += getHeuristicCost(it.getFirstValue(), it.getSecondValue());
        }

        return sum;
    }

    public static Double getHeuristicCost(G2DEntityState start, G2DEntityState end) {
        int startRow = start.get().get(0);
        int startCol = start.get().get(1);
        int endRow = end.get().get(0);
        int endCol = end.get().get(1);

        // return Math.sqrt(Math.pow(startRow - endRow, 2)
        //         + Math.pow(startCol - endCol, 2));

        double cost = (double) Math.abs(startRow - endRow) + Math.abs(startCol - endCol);
        return Math.max(cost, 1); // cost of staying in place is also 1
    }
}
