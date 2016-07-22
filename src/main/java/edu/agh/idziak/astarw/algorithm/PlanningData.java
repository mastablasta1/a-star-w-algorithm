package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class PlanningData<SS extends StateSpace<GS, U>, GS extends GlobalState<U>, U extends Comparable<U>> {
    private final InputPlan<SS, GS, U> inputPlan;
    private GlobalPath<U> path;
    private Set<DeviationZone<U>> deviationZones;

    PlanningData(InputPlan<SS, GS, U> inputPlan) {
        this.inputPlan = inputPlan;
        deviationZones = new HashSet<>();
    }

    public InputPlan<SS, GS, U> getInputPlan() {
        return inputPlan;
    }

    public GlobalPath<U> getPath() {
        return path;
    }

    public void setPath(GlobalPath<U> path) {
        this.path = path;
    }

    public Set<DeviationZone<U>> getDeviationZones() {
        return deviationZones;
    }

    public void setDeviationZones(Set<DeviationZone<U>> deviationZones) {
        this.deviationZones = deviationZones;
    }
}
