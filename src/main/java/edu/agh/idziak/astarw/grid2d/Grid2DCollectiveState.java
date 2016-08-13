package edu.agh.idziak.astarw.grid2d;

import com.google.common.collect.ImmutableMap;
import edu.agh.idziak.astarw.CollectiveState;
import edu.agh.idziak.astarw.EntityState;

import java.util.Map;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class Grid2DCollectiveState implements CollectiveState<Integer> {

    private Map<?, EntityState<Integer>> entityStates;

    private Grid2DCollectiveState() {
    }

    @Override
    public Map<?, EntityState<Integer>> getEntityStates() {
        return entityStates;
    }

    @Override
    public EntityState<Integer> getStateFor(Object entity) {
        return entityStates.get(entity);
    }

    public static Grid2DCollectiveState fromEntityStates(Map<?, EntityState<Integer>> entityStates) {
        Grid2DCollectiveState obj = new Grid2DCollectiveState();
        obj.entityStates = ImmutableMap.copyOf(entityStates);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && entityStates.equals(((Grid2DCollectiveState) o).entityStates);
    }

    @Override
    public int hashCode() {
        return entityStates.hashCode();
    }

    @Override
    public String toString() {
        return "CS:" + entityStates.toString();
    }
}
