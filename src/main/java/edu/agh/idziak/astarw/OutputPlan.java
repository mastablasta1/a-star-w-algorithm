package edu.agh.idziak.astarw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface OutputPlan<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> extends InputPlan<SS, GS, P, D> {

    GlobalPath<P> getGlobalPath();

    Set<DeviationZone<P>> getDeviationZones();
}
