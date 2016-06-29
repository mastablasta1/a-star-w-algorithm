package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DGlobalState implements GlobalState<Integer> {
    @Override
    public List<EntityState<Integer>> getEntityStates() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public EntityState<Integer> getEntityState(int globalStateIndex) {
        return null;
    }
}
