package pl.edu.agh.idziak.asw.impl.gridarray;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.common.CombinationsGenerator;
import pl.edu.agh.idziak.asw.common.Dictionary;
import pl.edu.agh.idziak.asw.common.Utils;
import pl.edu.agh.idziak.asw.model.StateSpace;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DStateSpace implements StateSpace<G2DCollectiveState> {

    private static final Logger LOG = LoggerFactory.getLogger(G2DStateSpace.class);

    private final int[][] space;
    private final Dictionary<Integer, G2DLightCollectiveState> stateSpace;

    public G2DStateSpace(int[][] space) {
        this.space = Preconditions.checkNotNull(space);
        stateSpace = new Dictionary<>();
    }

    @Deprecated
    private void initStateSpace(int numberOfEntities) {
        List<Integer> positionsX = IntStream.range(0, space.length).boxed().collect(toList());
        List<Integer> positionsY = IntStream.range(0, space[0].length).boxed().collect(toList());
        List<List<Integer>> combinationsSeed = new ArrayList<>();
        for (int i = 0; i < numberOfEntities; i++) {
            combinationsSeed.add(positionsX);
            combinationsSeed.add(positionsY);
        }
        Iterable<Integer> combinationsIterable = CombinationsGenerator.iterableCombinator(combinationsSeed);
        Iterator<Integer> it;
        while ((it = combinationsIterable.iterator()) != null) {
            stateSpace.put(it,new G2DLightCollectiveState());
        }
    }

    @Override
    public Set<G2DCollectiveState> getNeighborStatesOf(G2DCollectiveState collectiveState) {
        Map<?, G2DEntityState> entityStates = collectiveState.getEntityStates();
        HashMap<G2DEntityState, Object> stateToEntityMap = buildReversedMap(entityStates);

        Set<? extends Map.Entry<?, G2DEntityState>> sourceEntityStatesSet = entityStates.entrySet();

        List<List<G2DEntityState>> choiceArray = buildChoiceArray(sourceEntityStatesSet);

        List<List<G2DEntityState>> combinations = CombinationsGenerator.generateCombinations(
                choiceArray, G2DStateSpace::areAllEntityStatesUnique);

        Set<G2DCollectiveState> neighborStates = new HashSet<>();

        for (List<G2DEntityState> combination : combinations) {
            ImmutableMap.Builder<Object, G2DEntityState> neighborStateBuilder = ImmutableMap.builder();
            HashMap<Object, Object> sourceToTargetEntityExchange = new HashMap<>(combination.size());

            boolean entitiesDidNotCollide =
                    Utils.interruptableForEach(sourceEntityStatesSet, combination, (entry, targetEntityState) -> {
                        Object sourceEntity = stateToEntityMap.get(targetEntityState);
                        if (sourceEntity != null) {
                            Object targetEntity = sourceToTargetEntityExchange.get(entry.getKey());
                            if (Objects.equals(targetEntity, sourceEntity)) {
                                return false;
                            }
                            sourceToTargetEntityExchange.put(sourceEntity, entry.getKey());
                        }
                        neighborStateBuilder.put(entry.getKey(), targetEntityState);
                        return true;
                    });

            if (entitiesDidNotCollide) {
                G2DCollectiveState newNeighborState = G2DCollectiveState.from(neighborStateBuilder.build());
                neighborStates.add(newNeighborState);
            }
        }
        neighborStates.remove(collectiveState);

        LOG.trace("State {} has {} neighbors: {}", collectiveState, neighborStates.size(), neighborStates);
        return neighborStates;
    }

    private static HashMap<G2DEntityState, Object> buildReversedMap(Map<?, G2DEntityState> entityStates) {
        HashMap<G2DEntityState, Object> reversedMap = new HashMap<>(entityStates.size());
        for (Map.Entry<?, G2DEntityState> entry : entityStates.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }


    private List<List<G2DEntityState>> buildChoiceArray(Set<? extends Map.Entry<?, G2DEntityState>> entries) {
        List<List<G2DEntityState>> choiceArray = new LinkedList<>();
        for (Map.Entry<?, G2DEntityState> state : entries) {
            List<G2DEntityState> neighborStates = ImmutableList.copyOf(getNeighborStatesOf(state.getValue()));
            choiceArray.add(neighborStates);
        }
        return choiceArray;
    }

    private static boolean areAllEntityStatesUnique(List<G2DEntityState> entityStates) {
        Set<G2DEntityState> set = new HashSet<>();
        for (G2DEntityState entityState : entityStates) {
            if (set.contains(entityState))
                return false;
            set.add(entityState);
        }
        return true;
    }

    public Set<G2DEntityState> getNeighborStatesOf(G2DEntityState entityState) {
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
        if (weight <= 0) {
            states.add(new G2DEntityState(destRow, destCol));
        }
    }

    public int[][] getGridArray() {
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

    private static abstract class Visitor {

        public abstract void visit(G2DCollectiveState collectiveState);
    }
}
