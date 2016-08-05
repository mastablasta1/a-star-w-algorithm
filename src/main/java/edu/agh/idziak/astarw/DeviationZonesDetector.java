package edu.agh.idziak.astarw;

import edu.agh.idziak.astarw.algorithm.PlanningData;

import java.util.List;

public interface DeviationZonesDetector<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    List<EntityOutputPlan<P>> detectDeviationZones(PlanningData<SS, GS, P, D> planningData);

}
