package pl.edu.agh.idziak.asw.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class ImmutableEntityState<T extends Comparable<T>> implements EntityState<T> {

    private List<T> state;

    public ImmutableEntityState(T row, T col) {
        this.state = ImmutableList.of(row, col);
    }

    @Override
    public List<T> get() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && state.equals(((ImmutableEntityState) o).state);
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
