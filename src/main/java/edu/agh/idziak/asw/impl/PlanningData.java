package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class PlanningData<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    private final InputPlan<SS, GS, P, D> inputPlan;
    private CollectivePath<P> collectivePath;
    private Set<DeviationZone<P>> deviationZones;

    PlanningData(InputPlan<SS, GS, P, D> inputPlan) {
        this.inputPlan = inputPlan;
    }

    public InputPlan<SS, GS, P, D> getInputPlan() {
        return inputPlan;
    }

    public CollectivePath<P> getCollectivePath() {
        return collectivePath;
    }

    public Set<DeviationZone<P>> getDeviationZones() {
        return deviationZones;
    }

    void setCollectivePath(CollectivePath<P> collectivePath) {
        this.collectivePath = collectivePath;
    }

    void setDeviationZones(Set<DeviationZone<P>> deviationZones) {
        this.deviationZones = deviationZones;
    }
}
