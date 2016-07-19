package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface DeviationZone<U extends Comparable<U>> {
    Set<EntityState<U>> getStates();
}
