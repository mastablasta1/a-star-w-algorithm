package pl.edu.agh.idziak.asw.impl.gridarray;


import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Arrays;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class G2DOptCollectiveState implements CollectiveState<Integer> {

    private final byte[] state;
    private static final int BITS_PER_STATE = 4;
    private final int hashCode;

    public G2DOptCollectiveState(byte[] state) {
        this.state = state;
        this.hashCode = Arrays.hashCode(state);
    }

    private long encodeIdentity() {
        int length = state.length;
        long identity = 0;
        for (int i = 0; i < length; i++) {
            identity = (identity << BITS_PER_STATE) | state[i];
        }
        return identity;
    }

    byte[] getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && hashCode == o.hashCode()
                && Arrays.equals(state, ((G2DOptCollectiveState) o).state);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < state.length; i += 2) {
            sb.append("[").append(state[i]).append(",").append(state[i + 1]).append("] ");
        }
        return sb.append("}").toString();
    }
}
