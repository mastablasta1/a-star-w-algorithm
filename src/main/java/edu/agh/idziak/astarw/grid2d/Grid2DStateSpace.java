package edu.agh.idziak.astarw.grid2d;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.Position;
import edu.agh.idziak.astarw.StateSpace;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DStateSpace implements StateSpace<Integer> {

    private final int[][] space;

    public Grid2DStateSpace(int[][] space) {
        this.space = Preconditions.checkNotNull(space);
    }

    @Override
    public Set<GlobalState<Integer>> getNeighborStatesOf(GlobalState<Integer> globalState) {
        List<List<Integer>> choiceArray = new ArrayList<>();
        List<Integer> thisState = new ArrayList<>(globalState.get());

        for (int i = 0; i < thisState.size(); i++) {
            Integer dim = thisState.get(i);
            List<Integer> acc = new ArrayList<>();
            if (dim > 0)
                acc.add(dim-1);
            if(dim<)
                choiceArray.add(ImmutableList.of(dim + 1, dim - 1));
        }

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

    @Override
    public Set<EntityState<Integer>> getNeighborStatesOf(Iterable<EntityState<Integer>> entityStates) {
        return null;
    }


    private void addState(int destRow, int destCol, Set<EntityState<Integer>> states) {
        int weight = space[destRow][destCol];
        if (weight < 10) {
            states.add(new Grid2DEntityState(destRow, destCol));
        }
    }

    @Override
    public Integer getHeuristicDistance(Position<Integer> start, Position<Integer> end) {
        int startRow = start.get().get(0);
        int startCol = start.get().get(1);
        int endRow = end.get().get(0);
        int endCol = end.get().get(1);

        int colDist = Math.abs(startCol - endCol);

        return Math.abs(startRow - endRow) + colDist;
    }

    @Override
    public Integer getHeuristicDistance(GlobalState<Integer> start, GlobalState<Integer> end) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : space) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }
}
