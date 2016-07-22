package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DGlobalState implements GlobalState<Integer> {

    private List<Integer> state;
    private List<EntityState<Integer>> entityStates;

    private Grid2DGlobalState() {
    }

    @Override
    public List<EntityState<Integer>> getEntityStates() {
        return entityStates;
    }

    @Override
    public List<Integer> get() {
        return state;
    }

    public static Grid2DGlobalState fromEntityStates(List<EntityState<Integer>> entityStates) {
        Grid2DGlobalState obj = new Grid2DGlobalState();
        obj.entityStates = ImmutableList.copyOf(entityStates);
        return obj;
    }

    public static Grid2DGlobalState from(List<Integer> globalState) {
        if (globalState.size() % 2 != 0)
            throw new IllegalArgumentException("Grid2D global state must consist of pairs of coordinates");
        Grid2DGlobalState obj = new Grid2DGlobalState();
        obj.state = ImmutableList.copyOf(globalState);
        return obj;
    }

    @Override
    public int getEntitiesCount() {
        return state.size() / 2;
    }

    @Override
    public int getDimensionsPerEntity() {
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && state.equals(((Grid2DGlobalState) o).state);
    }

    @Override
    public int hashCode() {
        return entityStates.hashCode();
    }
}
