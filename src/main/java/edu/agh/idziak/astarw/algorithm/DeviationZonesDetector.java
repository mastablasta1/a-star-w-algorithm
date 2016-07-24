package edu.agh.idziak.astarw.algorithm;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.StateSpace;

import java.util.Set;

/**
 * Created by Tomasz on 18.07.2016.
 */
public class DeviationZonesDetector<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    private int minSafeDistance;

    public DeviationZonesDetector(int minSafeDistance) {
        this.minSafeDistance = minSafeDistance;
    }

    Set<DeviationZone<P>> detectDeviationZones(PlanningData<SS, GS, P, D> planningData) {
        Multiset<EntityState<P>> statesForPaths = HashMultiset.create();

        statesForPaths.removeIf(state -> statesForPaths.count(state) < 2);

        return null;
    }


}
