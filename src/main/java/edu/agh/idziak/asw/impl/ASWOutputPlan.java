package edu.agh.idziak.asw.impl;

import edu.agh.idziak.asw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWOutputPlan<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> implements OutputPlan<SS, CS, ES, P, D> {

    private InputPlan<SS, CS, ES, P, D> inputPlan;
    private CollectivePath<ES, P> collectivePath;
    private Set<DeviationZonePlan<P>> deviationZonePlans;

    public ASWOutputPlan(InputPlan<SS, CS, ES, P, D> inputPlan, CollectivePath<ES, P> collectivePath, Set<DeviationZonePlan<P>> deviationZonePlans) {
        this.inputPlan = inputPlan;
        this.collectivePath = collectivePath;
        this.deviationZonePlans = deviationZonePlans;
    }

    @Override
    public InputPlan<SS, CS, ES, P, D> getInputPlan() {
        return inputPlan;
    }

    @Override
    public CollectivePath<ES, P> getCollectivePath() {
        return collectivePath;
    }

    @Override
    public Set<DeviationZonePlan<P>> getDeviationZonePlans() {
        return deviationZonePlans;
    }
}
