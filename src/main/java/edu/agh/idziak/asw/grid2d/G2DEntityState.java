package edu.agh.idziak.asw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.asw.EntityState;

import java.util.List;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class G2DEntityState implements EntityState<Integer> {

    private List<Integer> state;

    public G2DEntityState(int row, int col) {
        this.state = ImmutableList.of(row, col);
    }

    public static G2DEntityState of(int row, int col) {
        return new G2DEntityState(row, col);
    }

    @Override
    public List<Integer> get() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && state.equals(((G2DEntityState) o).state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
