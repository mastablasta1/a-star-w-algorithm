package pl.edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface OutputPlan<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends
        EntityState<P>, P extends Comparable<P>, D extends Comparable<D>> {

    InputPlan<SS, CS, ES, P, D> getInputPlan();

    CollectivePath<CS, ES, P> getCollectivePath();

    Set<DeviationZonePlan<CS, ES, P>> getDeviationZonePlans();
}
