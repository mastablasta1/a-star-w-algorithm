package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface CollectivePath<P extends Comparable<P>> {
    List<CollectiveState<P>> get();

    List<EntityState<P>> getPathFor(Object entity);
}
