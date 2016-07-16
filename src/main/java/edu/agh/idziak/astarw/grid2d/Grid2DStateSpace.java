package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.StateSpace;
import edu.agh.idziak.astarw.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DStateSpace implements StateSpace<Integer> {

    private int[][] space = new int[][]{
            {1, 1, 1, 1},
            {1, 2, 2, 1},
            {1, 2, 2, 1},
            {1, 100, 1, 1}
    };

    @Override
    public Set<GlobalState<Integer>> getNeighborStatesOf(GlobalState<Integer> globalState) {
        return null;
    }

    @Override
    public Set<EntityState<Integer>> getNeighborStatesOf(EntityState<Integer> entityState) {
        List<Integer> positions = entityState.get();
        if (positions.size() != 2) {
            throw new IllegalStateException("Invalid state size, required=2, got=" + positions.size());
        }
        int row = positions.get(0);
        int col = positions.get(1);

        Set<EntityState<Integer>> states = new HashSet<>();

        if (row > 0) {
            addState(row - 1, col, states);
        }
        if (row < space.length - 1) {
            addState(row + 1, col, states);
        }
        if (col > 0) {
            addState(row, col - 1, states);
        }
        if (col < space[0].length - 1) {
            addState(row, col + 1, states);
        }

        return states;
    }


    private void addState(int destRow, int destCol, Set<EntityState<Integer>> states) {
        int weight = space[destRow][destCol];
        if (weight < 10) {
            states.add(new Grid2DEntityState(destRow, destCol));
        }
    }

    @Override
    public Integer getHeuristicDistance(Position<Integer> start, Position<Integer> end) {
        Integer startRow = start.get().get(0);
        Integer startCol = start.get().get(1);
        Integer endRow = end.get().get(0);
        Integer endCol = end.get().get(1);

        return Math.abs(startRow - endRow) + Math.abs(startCol - endCol);
    }
}
