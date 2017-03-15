package pl.edu.agh.idziak.asw.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Tomasz on 29.06.2016.
 * Objects of type ES must be immutable.
 */
public class ImmutableMapCollectiveState<ES extends EntityState<P>, P extends Comparable<P>> implements
        CollectiveState<ES, P> {

    private final Map<?, ES> entityStates;
    private final int hashCode;

    public ImmutableMapCollectiveState(Map<?, ES> entityStates) {
        this.entityStates = ImmutableMap.copyOf(entityStates);
        this.hashCode = entityStates.hashCode();
    }

    @Override
    public Map<?, ES> getEntityStates() {
        return entityStates;
    }

    @Override
    public ES getStateForEntity(Object entity) {
        return entityStates.get(entity);
    }

    public boolean isValid() {
        long count = entityStates.values()
                                 .stream()
                                 .distinct()
                                 .count();
        return count == entityStates.size();
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || !(o == null || getClass() != o.getClass())
                && hashCode == o.hashCode()
                && entityStates.equals(((ImmutableMapCollectiveState) o).entityStates);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "CS:" + entityStates.toString();
    }
}
