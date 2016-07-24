package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class PlanningData<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    private final InputPlan<SS, GS, P, D> inputPlan;
    private GlobalPath<P> path;
    private Set<DeviationZone<P>> deviationZones;

    PlanningData(InputPlan<SS, GS, P, D> inputPlan) {
        this.inputPlan = inputPlan;
        deviationZones = new HashSet<>();
    }

    public InputPlan<SS, GS, P, D> getInputPlan() {
        return inputPlan;
    }

    public GlobalPath<P> getPath() {
        return path;
    }

    public void setPath(GlobalPath<P> path) {
        this.path = path;
    }

}
