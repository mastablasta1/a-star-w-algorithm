package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.GlobalStateSpace;
import edu.agh.idziak.astarw.Position;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DGlobalStateSpace implements GlobalStateSpace<Integer> {

    @Override
    public Set<GlobalState<Integer>> getNeighborStatesOf(GlobalState<Integer> globalState) {
        return null;
    }

    @Override
    public Set<EntityState<Integer>> getNeighborStatesOf(EntityState<Integer> entityState) {
        return null;
    }

    @Override
    public int getStateSize() {
        return 0;
    }

    @Override
    public Integer getHeuristicDistance(Position<Integer> start, Position<Integer> end) {
        return null;
    }
}
