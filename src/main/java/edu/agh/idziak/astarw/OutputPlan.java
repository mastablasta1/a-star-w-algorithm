package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface OutputPlan<SS extends StateSpace<GS, U>, GS extends GlobalState<U>, U extends Comparable<U>> extends InputPlan<SS, GS, U> {

    GlobalPath<U> getGlobalPath();

    Set<DeviationZone<U>> getDeviationZones();
}
