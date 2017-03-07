package pl.edu.agh.idziak.asw.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by Tomasz on 09.07.2016.
 * Objects of type T have to be immutable.
 */
public class ImmutableEntityState<T extends Comparable<T>> implements EntityState<T> {

    private final List<T> state;
    private final int hashCode;

    public ImmutableEntityState(T row, T col) {
        this.state = ImmutableList.of(row, col);
        this.hashCode = state.hashCode();
    }

    @Override
    public List<T> get() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && hashCode == o.hashCode()
                && state.equals(((ImmutableEntityState) o).state);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
