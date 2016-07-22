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
public class DeviationZonesDetector<SS extends StateSpace<U>,GS extends GlobalState<U>, U extends Comparable<U>> {

    private int minSafeDistance;

    public DeviationZonesDetector(int minSafeDistance) {
        this.minSafeDistance = minSafeDistance;
    }

    Set<DeviationZone<U>> detectDeviationZones(PlanningData<SS, GS, U> planningData) {
        Multiset<EntityState<U>> statesForPaths = HashMultiset.create();

        statesForPaths.removeIf(state -> statesForPaths.count(state) < 2);

        return null;
    }


}
