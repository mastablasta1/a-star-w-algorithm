package edu.agh.idziak.asw.grid2d;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.agh.idziak.asw.EntityState;
import edu.agh.idziak.asw.StateSpace;
import edu.agh.idziak.common.CombinationsGenerator;
import edu.agh.idziak.common.PairIterator;
import edu.agh.idziak.common.UntypedTwoMapsIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DStateSpace implements StateSpace<Grid2DCollectiveState, Integer, Double> {
    private static final Logger LOG = LoggerFactory.getLogger(Grid2DStateSpace.class);

    private final int[][] space;

    public Grid2DStateSpace(int[][] space) {
        this.space = Preconditions.checkNotNull(space);
    }

    private boolean areAllStatesUnique(List<Grid2DEntityState> entityStates) {
        Set<Grid2DEntityState> set = new HashSet<>();
        for (Grid2DEntityState entityState : entityStates) {
            if (set.contains(entityState))
                return false;
            set.add(entityState);
        }
        return true;
    }

    public Set<Grid2DEntityState> getNeighborStatesOf(Grid2DEntityState entityState) {
        List<Integer> positions = entityState.get();
        int row = positions.get(0);
        int col = positions.get(1);

        Set<Grid2DEntityState> states = new HashSet<>();

        addState(row, col, states);
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

    private void addState(int destRow, int destCol, Set<Grid2DEntityState> states) {
        int weight = space[destRow][destCol];
        if (weight < 10) {
            states.add(new Grid2DEntityState(destRow, destCol));
        }
    }

    @Override
    public Double getHeuristicDistance(EntityState<Integer> start, EntityState<Integer> end) {
        int startRow = start.get().get(0);
        int startCol = start.get().get(1);
        int endRow = end.get().get(0);
        int endCol = end.get().get(1);

//        return Math.sqrt(Math.pow(startRow - endRow, 2)
//                + Math.pow(startCol - endCol, 2));
        return (double) Math.abs(startRow - endRow) + Math.abs(startCol - endCol);
    }

    @Override
    public Double getHeuristicDistance(Grid2DCollectiveState start, Grid2DCollectiveState end) {
        Map<?, Grid2DEntityState> startStates = start.getEntityStates();
        Map<?, Grid2DEntityState> endStates = end.getEntityStates();

        UntypedTwoMapsIterator<Grid2DEntityState> it = new UntypedTwoMapsIterator<>(startStates, endStates);

        double sum = 0;

        while (it.hasNext()) {
            it.next();
            sum += getHeuristicDistance(it.getFirstValue(), it.getSecondValue());
        }

        return sum;
    }

    @Override
    public Set<Grid2DCollectiveState> getNeighborStatesOf(Grid2DCollectiveState collectiveState) {
        List<List<Grid2DEntityState>> choiceArray = new ArrayList<>();

        Map<?, Grid2DEntityState> entityStates = collectiveState.getEntityStates();

        List<Map.Entry<?, Grid2DEntityState>> entries = ImmutableList.copyOf(entityStates.entrySet());

        buildChoiceArray(choiceArray, entries);

        List<List<Grid2DEntityState>> combinations = CombinationsGenerator.generateCombinations(choiceArray, this::areAllStatesUnique);

        Set<Grid2DCollectiveState> neighborStates = new HashSet<>();

        for (List<Grid2DEntityState> combination : combinations) {
            PairIterator<Map.Entry<?, Grid2DEntityState>, Grid2DEntityState> it = new PairIterator<>(entries, combination);

            ImmutableMap.Builder<Object, Grid2DEntityState> builder = ImmutableMap.builder();
            while (it.hasNext()) {
                it.next();
                builder.put(it.getCurrentA().getKey(), it.getCurrentB());
            }
            ImmutableMap<Object, Grid2DEntityState> build = builder.build();
            Grid2DCollectiveState neighbor = Grid2DCollectiveState.fromEntityStates(build);
            neighborStates.add(neighbor);
        }

        LOG.trace("State {} has {} neighbors: {}", collectiveState, neighborStates.size(), neighborStates);
        return neighborStates;
    }

    private void buildChoiceArray(List<List<Grid2DEntityState>> choiceArray, List<Map.Entry<?, Grid2DEntityState>> entries) {
        for (Map.Entry<?, Grid2DEntityState> state : entries) {
            List<Grid2DEntityState> neighborStates = ImmutableList.copyOf(getNeighborStatesOf(state.getValue()));
            choiceArray.add(neighborStates);
        }
    }

    int[][] getTable() {
        return space;
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
