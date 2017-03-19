package pl.edu.agh.idziak.asw.impl.gridarray;

import pl.edu.agh.idziak.asw.model.CostFunction;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class G2DCostFunction implements CostFunction<G2DOptCollectiveState, Double> {

    public Double getHeuristicCostEstimate(G2DOptCollectiveState start, G2DOptCollectiveState goal) {
        byte[] state1 = start.getState();
        byte[] state2 = goal.getState();
        double sum = 0;
        for (int i = 0; i < state1.length; i += 2) {
            sum += getHeuristicCostEstimate(state1[i], state1[i + 1], state2[i], state2[i + 1]);
        }
        return sum;
    }

    private static Double getHeuristicCostEstimate(byte startRow, byte startCol, byte endRow, byte endCol) {
        return Math.sqrt(Math.pow(startRow - endRow, 2) + Math.pow(startCol - endCol, 2));
    }

    @Override
    public Double getDistanceBetween(G2DOptCollectiveState state, G2DOptCollectiveState neighbor) {
        byte[] state1 = state.getState();
        byte[] state2 = neighbor.getState();
        double sum = 0;
        for (int i = 0; i < state1.length; i += 2) {
            sum += getDistanceBetween(state1[i], state1[i + 1], state2[i], state2[i + 1]);
        }
        return sum;
    }

    private double getDistanceBetween(byte startRow, byte startCol, byte endRow, byte endCol) {
        return getHeuristicCostEstimate(startRow, startCol, endRow, endCol);
    }
}
