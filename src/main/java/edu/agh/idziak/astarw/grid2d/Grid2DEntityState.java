package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;

import java.util.List;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class Grid2DEntityState implements EntityState<Integer> {

    private List<Integer> state;

    public Grid2DEntityState(int row, int col) {
        this.state = ImmutableList.of(row, col);
    }

    public static Grid2DEntityState of(int row, int col) {
        return new Grid2DEntityState(row, col);
    }

    @Override
    public List<Integer> get() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && state.equals(((Grid2DEntityState) o).state);
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
