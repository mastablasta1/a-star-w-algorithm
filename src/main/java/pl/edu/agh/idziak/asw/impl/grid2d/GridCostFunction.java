package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.model.CostFunction;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class GridCostFunction implements CostFunction<GridCollectiveState, Double> {

    public Double getHeuristicCostEstimate(GridCollectiveState start, GridCollectiveState goal) {
        byte[] state1 = start.getArray();
        byte[] state2 = goal.getArray();
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
    public Double getDistanceBetween(GridCollectiveState state, GridCollectiveState neighbor) {
        byte[] state1 = state.getArray();
        byte[] state2 = neighbor.getArray();
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
