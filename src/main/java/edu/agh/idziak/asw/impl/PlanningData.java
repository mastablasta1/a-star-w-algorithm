package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class PlanningData<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    private final InputPlan<SS, CS, ES, P, D> inputPlan;
    private CollectivePath<ES, P> collectivePath;
    private Set<DeviationZone<P>> deviationZones;

    PlanningData(InputPlan<SS, CS, ES, P, D> inputPlan) {
        this.inputPlan = inputPlan;
    }

    public InputPlan<SS, CS, ES, P, D> getInputPlan() {
        return inputPlan;
    }

    public CollectivePath<ES, P> getCollectivePath() {
        return collectivePath;
    }

    public Set<DeviationZone<P>> getDeviationZones() {
        return deviationZones;
    }

    void setCollectivePath(CollectivePath<ES, P> collectivePath) {
        this.collectivePath = collectivePath;
    }

    void setDeviationZones(Set<DeviationZone<P>> deviationZones) {
        this.deviationZones = deviationZones;
    }
}
