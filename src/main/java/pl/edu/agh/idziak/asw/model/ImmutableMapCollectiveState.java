package pl.edu.agh.idziak.asw.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ImmutableMapCollectiveState<ES extends EntityState<P>, P extends Comparable<P>> implements
        CollectiveState<ES, P> {

    private Map<?, ES> entityStates;

    public ImmutableMapCollectiveState(Map<?, ES> entityStates) {
        this.entityStates = ImmutableMap.copyOf(entityStates);
    }

    @Override
    public Map<?, ES> getEntityStates() {
        return entityStates;
    }

    @Override
    public ES getStateForEntity(Object entity) {
        return entityStates.get(entity);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && entityStates.equals(((ImmutableMapCollectiveState) o).entityStates);
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
