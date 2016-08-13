package edu.agh.idziak.asw.logic;

import edu.agh.idziak.asw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ASWOutputPlan<E, SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> implements OutputPlan<SS, GS, P, D> {

    private InputPlan<SS, GS, P, D> inputPlan;
    private CollectivePath<P> collectivePath;
    private Set<DeviationZone<P>> deviationZones;

    public ASWOutputPlan(InputPlan<SS, GS, P, D> inputPlan, CollectivePath<P> collectivePath, Set<DeviationZone<P>> deviationZones) {
        this.inputPlan = inputPlan;
        this.collectivePath = collectivePath;
        this.deviationZones = deviationZones;
    }

    @Override
    public InputPlan<SS, GS, P, D> getInputPlan() {
        return inputPlan;
    }

    @Override
    public CollectivePath<P> getCollectivePath() {
        return collectivePath;
    }

    @Override
    public Set<DeviationZone<P>> getDeviationZones() {
        return deviationZones;
    }
}
