package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface GlobalState<U extends Comparable<U>> {
    List<EntityState<U>> getEntityStates();
    List<U> get();

    int getEntitiesCount();
    int getDimensionsPerEntity();

}
