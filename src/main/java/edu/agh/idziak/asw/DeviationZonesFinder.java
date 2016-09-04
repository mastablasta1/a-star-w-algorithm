package edu.agh.idziak.asw;

import edu.agh.idziak.asw.impl.PlanningData;

import java.util.Set;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface DeviationZonesFinder<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES
        extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> {

    Set<DeviationZone<CS, ES, P>> findDeviationZones(PlanningData<SS, CS, ES, P, D> planningData);
}
