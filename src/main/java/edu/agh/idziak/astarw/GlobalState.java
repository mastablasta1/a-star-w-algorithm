package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface GlobalState<P extends Comparable<P>> {
    List<EntityState<P>> getEntityStates();

    int getEntitiesCount();
    int getDimensionsPerEntity();

}
