package pl.edu.agh.idziak.asw.impl.grid2d;


import com.google.common.collect.ImmutableList;
import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Iterator;
import java.util.List;

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

    public boolean isReducedFrom(GridCollectiveState higherOrderState, List<Integer> entityIndexes) {
        Iterator<GridEntityState> thisIt = getEntityStates().iterator();

        for (Integer index : entityIndexes) {
            if (!thisIt.hasNext() || index >= higherOrderState.entityStatesCount())
                return false;
            GridEntityState state = higherOrderState.getEntityStates().get(index);
            if (!state.equals(thisIt.next())) {
                return false;
            }
        }
        return true;
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
