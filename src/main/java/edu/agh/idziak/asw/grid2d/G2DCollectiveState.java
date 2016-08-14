package edu.agh.idziak.asw.grid2d;

import com.google.common.collect.ImmutableMap;
import edu.agh.idziak.asw.CollectiveState;
import edu.agh.idziak.asw.EntityState;

import java.util.Map;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DCollectiveState implements CollectiveState<Integer> {

    private Map<?, G2DEntityState> entityStates;

    private G2DCollectiveState() {
    }

    @Override
    public Map<?, G2DEntityState> getEntityStates() {
        return entityStates;
    }

    @Override
    public EntityState<Integer> getStateFor(Object entity) {
        return entityStates.get(entity);
    }

    public static G2DCollectiveState fromEntityStates(Map<?, G2DEntityState> entityStates) {
        G2DCollectiveState obj = new G2DCollectiveState();
        obj.entityStates = ImmutableMap.copyOf(entityStates);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && entityStates.equals(((G2DCollectiveState) o).entityStates);
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
