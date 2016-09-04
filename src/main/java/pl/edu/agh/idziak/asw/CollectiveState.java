package pl.edu.agh.idziak.asw;

import java.util.Map;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface CollectiveState<ES extends EntityState<P>, P extends Comparable<P>> {
    Map<?, ES> getEntityStates();

    ES getStateForEntity(Object entity);
}
