package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface DeviationZone<P extends Comparable<P>> {
    Set<EntityState<P>> getStates();
}
