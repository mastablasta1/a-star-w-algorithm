package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DGlobalState implements GlobalState<Integer> {

    private List<EntityState<Integer>> state;

    private Grid2DGlobalState() {
    }

    @Override
    public List<EntityState<Integer>> getEntityStates() {
        return state;
    }

    public static Grid2DGlobalState from(List<EntityState<Integer>> entityStates) {
        Grid2DGlobalState obj = new Grid2DGlobalState();
        obj.state = ImmutableList.copyOf(entityStates);
        return obj;
    }

    @Override
    public int getSize() {
        return state.size();
    }

}
