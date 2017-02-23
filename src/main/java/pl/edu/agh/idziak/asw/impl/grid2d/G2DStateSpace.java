package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.common.CombinationsGenerator;
import pl.edu.agh.idziak.asw.common.PairIterator;
import pl.edu.agh.idziak.asw.model.*;

import java.util.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DStateSpace implements StateSpace<G2DCollectiveState> {
    private static final Logger LOG = LoggerFactory.getLogger(G2DStateSpace.class);

    private final int[][] space;

    public G2DStateSpace(int[][] space) {
        this.space = Preconditions.checkNotNull(space);
    }

    private static boolean areAllStatesUnique(List<G2DEntityState> entityStates) {
        Set<G2DEntityState> set = new HashSet<>();
        for (G2DEntityState entityState : entityStates) {
            if (set.contains(entityState))
                return false;
            set.add(entityState);
        }
        return true;
    }


    private Set<G2DEntityState> getNeighborStatesOf(EntityState<Integer> entityState) {
        List<Integer> positions = entityState.get();
        int row = positions.get(0);
        int col = positions.get(1);

        Set<G2DEntityState> states = new HashSet<>();

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

    private void addState(int destRow, int destCol, Set<G2DEntityState> states) {
        int weight = space[destRow][destCol];
        if (weight < 10) {
            states.add(new G2DEntityState(destRow, destCol));
        }
    }

    @Override
    public Set<G2DCollectiveState> getNeighborStatesOf(G2DCollectiveState collectiveState) {
        List<List<G2DEntityState>> choiceArray = new ArrayList<>();

        Map<?, G2DEntityState> entityStates = collectiveState.getEntityStates();

        List<Map.Entry<?, G2DEntityState>> entries = ImmutableList.copyOf(entityStates.entrySet());

        buildChoiceArray(choiceArray, entries);

        List<List<G2DEntityState>> combinations = CombinationsGenerator.generateCombinations(choiceArray,
                G2DStateSpace::areAllStatesUnique);

        Set<G2DCollectiveState> neighborStates = new HashSet<>();

        for (List<G2DEntityState> combination : combinations) {
            PairIterator<Map.Entry<?, G2DEntityState>, G2DEntityState> it = new PairIterator<>(entries, combination);

            ImmutableMap.Builder<Object, G2DEntityState> builder = ImmutableMap.builder();
            while (it.hasNext()) {
                it.next();
                builder.put(it.getCurrentA().getKey(), it.getCurrentB());
            }
            ImmutableMap<?, G2DEntityState> states = builder.build();
            G2DCollectiveState neighbor = new G2DCollectiveState(states);
            neighborStates.add(neighbor);
        }

        LOG.trace("State {} has {} neighbors: {}", collectiveState, neighborStates.size(), neighborStates);
        return neighborStates;
    }

    private void buildChoiceArray(List<List<G2DEntityState>> choiceArray, List<Map.Entry<?, G2DEntityState>> entries) {
        for (Map.Entry<?, G2DEntityState> state : entries) {
            List<G2DEntityState> neighborStates = ImmutableList.copyOf(getNeighborStatesOf(state.getValue()));
            choiceArray.add(neighborStates);
        }
    }

    public int[][] getData() {
        return space;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : space) {
            sb.append(Arrays.toString(row))
              .append("\n");
        }
        return sb.toString();
    }

    public int countRows() {
        return space.length;
    }

    public int countCols() {
        return space[0].length;
    }
}
