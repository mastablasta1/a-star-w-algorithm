package edu.agh.idziak.asw.impl;

import com.google.common.base.Preconditions;
import edu.agh.idziak.asw.*;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> implements Planner<SS, CS, ES, P, D> {

    private final CollectiveAStar<SS, CS, ES, D, P> collectiveAStar;
    private final WaveFront<SS, CS, ES, D, P> waveFront;
    private final DeviationZonesFinder<SS, CS, ES, D, P> deviationZonesFinder;

    public ASWPlanner(AbstractNumberHandler<D> abstractNumberHandler, DeviationZonesFinder<SS, CS, ES, D, P> deviationZonesFinder) {
        this.deviationZonesFinder = Preconditions.checkNotNull(deviationZonesFinder);
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        collectiveAStar = new CollectiveAStar<>(abstractNumberHandler);
        waveFront = new WaveFront<>(abstractNumberHandler);
    }

    @Override
    public OutputPlan<SS, CS, ES, P, D> calculatePlan(InputPlan<SS, CS, ES, P, D> inputPlan) {
        validate(inputPlan);

        PlanningData<SS, CS, ES, P, D> planningData = new PlanningData<>(inputPlan);

        ImmutableCollectivePath<CS, ES, P> path = collectiveAStar.calculatePath(planningData);
        planningData.setCollectivePath(path);

        Set<DeviationZone<CS, ES, P>> deviationZones = deviationZonesFinder.findDeviationZones(planningData);

        Preconditions.checkNotNull(deviationZones, "DeviationZonesFinder must return a set (can be empty).");
        planningData.setDeviationZones(deviationZones);

        Set<DeviationZonePlan<CS, ES, P>> deviationZonePlans = waveFront.makeDeviationZonePlans(planningData);

        return new ASWOutputPlan<>(inputPlan, path, deviationZonePlans);
    }

    private static void validate(InputPlan inputPlan) {
        Preconditions.checkArgument(inputPlan.getInitialGlobalState().getEntityStates().size() == inputPlan.getTargetGlobalState().getEntityStates().size(), "Initial and target state sizes mismatch");
    }
}
