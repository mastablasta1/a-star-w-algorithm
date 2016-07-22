package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DGlobalState implements GlobalState<Integer> {

    private List<EntityState<Integer>> entityStates;

    private Grid2DGlobalState() {
    }

    @Override
    public List<EntityState<Integer>> getEntityStates() {
        return entityStates;
    }

    public static Grid2DGlobalState fromEntityStates(List<EntityState<Integer>> entityStates) {
        Grid2DGlobalState obj = new Grid2DGlobalState();
        obj.entityStates = ImmutableList.copyOf(entityStates);
        return obj;
    }

    @Override
    public int getEntitiesCount() {
        return entityStates.size();
    }

    @Override
    public int getDimensionsPerEntity() {
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && entityStates.equals(((Grid2DGlobalState) o).entityStates);
    }

    @Override
    public int hashCode() {
        return entityStates.hashCode();
    }

    @Override
    public String toString() {
        return "GS:"+entityStates.toString();
    }
}
