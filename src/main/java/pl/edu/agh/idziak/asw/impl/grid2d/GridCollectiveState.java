package pl.edu.agh.idziak.asw.impl.grid2d;


import com.google.common.collect.ImmutableList;
import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class GridCollectiveState implements CollectiveState {

    private final byte[] state;
    private List<GridEntityState> entityStatesList;

    public GridCollectiveState(byte[] state) {
        this.state = checkNotNull(state);
    }

    public GridCollectiveState(List<Integer> state) {
        this.state = new byte[state.size()];
        for (int i = 0; i < state.size(); i++) {
            this.state[i] = (byte) (int) state.get(i);
        }
    }

    byte[] getArray() {
        return state;
    }

    public List<GridEntityState> getEntityStates() {
        if (entityStatesList == null) {
            ImmutableList.Builder<GridEntityState> builder = ImmutableList.builder();
            for (int i = 0; i < state.length; i += 2) {
                builder.add(GridEntityState.of(state[i], state[i + 1]));
            }
            entityStatesList = builder.build();
        }
        return entityStatesList;
    }

    public int entityStatesCount() {
        return state.length / 2;
    }

    public static GridCollectiveState fromEntityStates(List<GridEntityState> entityStates) {
        List<Integer> flatList = entityStates.stream()
                .flatMap(s -> Stream.of(s.getRow(), s.getCol()))
                .collect(Collectors.toList());
        return new GridCollectiveState(flatList);
    }

    public static GridCollectiveState fromEntityStates(GridEntityState... entityStates) {
        return fromEntityStates(Arrays.asList(entityStates));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < state.length; i += 2) {
            sb.append("[").append(state[i]).append(",").append(state[i + 1]).append("] ");
        }
        sb.setLength(sb.length() - 1);
        return sb.append("}").toString();
    }

    @Override
    public int size() {
        return state.length;
    }
}
