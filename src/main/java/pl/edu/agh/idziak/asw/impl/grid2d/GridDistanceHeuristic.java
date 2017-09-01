package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.model.DistanceHeuristic;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class GridDistanceHeuristic implements DistanceHeuristic<GridCollectiveState, Double> {

    private static final double SQRT_OF_2 = Math.sqrt(2.0);
    public static final double OCTILE_COEF = SQRT_OF_2 - 2;
    private NeighborhoodType neighborhoodType;
    private GridCollectiveState goal;

    public GridDistanceHeuristic(NeighborhoodType neighborhoodType) {
        this.neighborhoodType = neighborhoodType;
    }

    @Override
    public Double estimateHeuristicDistance(GridCollectiveState start, GridCollectiveState goal) {
        byte[] state1 = start.getArray();
        byte[] state2 = goal.getArray();
        double sum = 0;
        for (int i = 0; i < state1.length; i += 2) {
            sum += estimateEntityHeuristicDistance(state1[i], state1[i + 1], state2[i], state2[i + 1]);
        }
        return sum;
    }

    private Double estimateEntityHeuristicDistance(byte startRow, byte startCol, byte endRow, byte endCol) {
        if (neighborhoodType == NeighborhoodType.VON_NEUMANN) {
            return Math.abs((double) startCol - endCol) + Math.abs(startRow - endRow);
        } else {
            int dx = Math.abs(startCol - endCol);
            int dy = Math.abs(startRow - endRow);
            return dx + dy + OCTILE_COEF * Math.min(dx, dy);
        }
    }

    public Double estimateHeuristicDistanceForEntity(GridEntityState start, GridEntityState goal) {
        return estimateEntityHeuristicDistance(
                (byte) start.getRow(), (byte) start.getCol(),
                (byte) goal.getRow(), (byte) goal.getCol());
    }

    @Override
    public Double getDistanceBetween(GridCollectiveState state, GridCollectiveState neighbor) {
        byte[] state1 = state.getArray();
        byte[] state2 = neighbor.getArray();
        byte[] goalArray = goal.getArray();
        double sum = 0;
        for (int i = 0; i < state1.length; i += 2) {
            double dist = getDistanceBetweenEntityStates(state1[i], state1[i + 1], state2[i], state2[i + 1]);
            if (goalArray[i] == state1[i] && goalArray[i + 1] == state1[i + 1]) {
                dist = 0d;
            }
            sum += dist;
        }
        return sum;
    }

    public void setGoal(GridCollectiveState goal) {
        this.goal = goal;
    }

    private double getDistanceBetweenEntityStates(byte startRow, byte startCol, byte endRow, byte endCol) {
        assert Math.abs(startCol - endCol) <= 1;
        assert Math.abs(startRow - endRow) <= 1;

        if (startCol != endCol && startRow != endRow) {
            return SQRT_OF_2;
        }
        return 1d;
        // if (startCol != endCol) {
        //     if (startRow != endRow) {
        //         return SQRT_OF_2;
        //     }
        //     return 1;
        // }
        // if (startRow != endRow) {
        //     return 1;
        // }
        // return 0d;
    }
}
