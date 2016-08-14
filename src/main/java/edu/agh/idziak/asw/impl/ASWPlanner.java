package edu.agh.idziak.asw.impl;

import com.google.common.base.Preconditions;
import edu.agh.idziak.asw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<CS, P, D>, CS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> implements Planner<SS, CS, P, D> {

    private final CollectiveAStar<SS, CS, P, D> collectiveAStar;
    private final WaveFront<SS, CS,P,D> waveFront;
    private final DeviationZonesFinder<SS, CS, P, D> deviationZonesFinder;

    public ASWPlanner(AbstractNumberHandler<D> abstractNumberHandler, DeviationZonesFinder<SS, CS, P, D> deviationZonesFinder) {
        this.deviationZonesFinder = Preconditions.checkNotNull(deviationZonesFinder);
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        collectiveAStar = new CollectiveAStar<>(abstractNumberHandler);
        waveFront = new WaveFront<>(abstractNumberHandler);
    }

    @Override
    public OutputPlan<SS, CS, P, D> calculatePlan(InputPlan<SS, CS, P, D> inputPlan) {
        validate(inputPlan);

        PlanningData<SS, CS, P, D> planningData = new PlanningData<>(inputPlan);

        ImmutableCollectivePath<P> path = collectiveAStar.calculatePath(planningData);

        planningData.setCollectivePath(path);

        Set<DeviationZone<P>> deviationZones = deviationZonesFinder.findDeviationZones(planningData);

        waveFront.makeDeviationZonePlans(planningData);

        return new ASWOutputPlan<>(inputPlan, path, null);
    }

    private static void validate(InputPlan inputPlan) {
        Preconditions.checkArgument(inputPlan.getInitialGlobalState().getEntityStates().size() == inputPlan.getTargetGlobalState().getEntityStates().size(), "Initial and target state sizes mismatch");
    }
}
