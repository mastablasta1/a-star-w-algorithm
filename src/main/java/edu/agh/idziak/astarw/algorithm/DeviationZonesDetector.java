package edu.agh.idziak.astarw.algorithm;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.Path;
import edu.agh.idziak.astarw.StateSpace;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomasz on 18.07.2016.
 */
public class DeviationZonesDetector<SS extends StateSpace<U>, U extends Comparable<U>> {

    private int minSafeDistance;

    public DeviationZonesDetector(int minSafeDistance) {
        this.minSafeDistance = minSafeDistance;
    }

    Set<DeviationZone<U>> detectDeviationZones(Iterable<Path<U>> paths, SS stateSpace) {
        Multiset<EntityState<U>> statesForPaths = HashMultiset.create();

        for (Path<U> path : paths) {
            processPath(path, statesForPaths, stateSpace);
        }

        statesForPaths.removeIf(state -> statesForPaths.count(state) < 2);



        return null;
    }

    private void processPath(Path<U> path, Multiset<EntityState<U>> statesForPaths, SS stateSpace) {
        List<EntityState<U>> pathList = path.get();
        for (EntityState<U> state : pathList) {
            statesForPaths.add(state);
            Set<EntityState<U>> statesInProximity = exploreUnsafeDistance(state, stateSpace);
            statesForPaths.addAll(statesInProximity);
        }
    }

    private Set<EntityState<U>> exploreUnsafeDistance(EntityState<U> state, SS stateSpace) {
        Set<EntityState<U>> statesInProximity = new HashSet<>();
        statesInProximity.add(state);
        Set<EntityState<U>> neighbors = Collections.singleton(state);

        for (int i = 0; i < minSafeDistance; i++) {
            neighbors = stateSpace.getNeighborStatesOf(neighbors);
            statesInProximity.addAll(neighbors);
        }

        return statesInProximity;
    }
}
