package pl.edu.agh.idziak.asw.impl.grid2d;


import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class GridCollectiveState implements CollectiveState<Integer> {

    private final byte[] state;

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
        List<GridEntityState> entityStates = new ArrayList<>(state.length);
        for (int i = 0; i < state.length; i += 2) {
            entityStates.add(GridEntityState.of(state[i], state[i + 1]));
        }
        return entityStates;
    }

    public int entityStatesCount() {
        return state.length / 2;
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

}
