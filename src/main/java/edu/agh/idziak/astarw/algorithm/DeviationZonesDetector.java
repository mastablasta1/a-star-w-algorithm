package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.StateSpace;

import java.util.Collection;
import java.util.List;

public interface DeviationZonesDetector<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    List<DeviationZone<P>> detectDeviationZones(PlanningData<SS, GS, P, D> planningData);

}
