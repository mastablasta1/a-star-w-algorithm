package edu.agh.idziak.asw;

import edu.agh.idziak.asw.impl.PlanningData;

import java.util.Set;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface DeviationZonesFinder<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    Set<DeviationZone<P>> findDeviationZones(PlanningData<SS, GS, P, D> planningData);
}
