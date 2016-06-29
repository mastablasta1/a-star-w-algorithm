package edu.agh.idziak.astarw;

import java.util.List;
import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface OutputPlan<SS extends GlobalStateSpace<U>, GS extends GlobalState<U>, U extends Comparable<U>> extends InputPlan<SS, GS, U> {
    List<Path<U>> getPaths();

    Set<DeviationZone<U>> getDeviationZones();
}
