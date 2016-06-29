package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class PlanningOperation<SS extends GlobalStateSpace<U>, S extends GlobalState<U>, U extends Comparable<U>> {
    private final InputPlan<SS, S, U> inputPlan;
    private final List<Path<U>> paths;
    private final Set<DeviationZone<U>> deviationZones;

    PlanningOperation(InputPlan<SS, S, U> inputPlan) {
        this.inputPlan = inputPlan;
        paths = new ArrayList<>(inputPlan.getStateSpace().getStateSize());
        deviationZones =  new HashSet<>();
    }

    public InputPlan<SS, S, U> getInputPlan() {
        return inputPlan;
    }

    public List<Path<U>> getPaths() {
        return paths;
    }

    public Set<DeviationZone<U>> getDeviationZones() {
        return deviationZones;
    }
}
