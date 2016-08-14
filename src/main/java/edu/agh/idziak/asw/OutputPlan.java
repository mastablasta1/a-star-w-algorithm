package edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface OutputPlan<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    InputPlan<SS, GS, P, D> getInputPlan();

    CollectivePath<P> getCollectivePath();

    Set<DeviationZonePlan<P>> getDeviationZonePlans();
}
