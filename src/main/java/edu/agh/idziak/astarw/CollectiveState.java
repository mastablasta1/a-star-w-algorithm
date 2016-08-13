package edu.agh.idziak.astarw;

import java.util.Map;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface CollectiveState<P extends Comparable<P>> {
    Map<?, EntityState<P>> getEntityStates();

    EntityState<P> getStateFor(Object entity);
}
