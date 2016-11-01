package pl.edu.agh.idziak.asw.impl;

import com.google.common.base.Preconditions;
import pl.edu.agh.idziak.asw.*;
import pl.edu.agh.idziak.common.Statistics;
import pl.edu.agh.idziak.common.StatisticsSource;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends
        EntityState<P>, D extends Comparable<D>, P extends Comparable<P>> implements Planner<SS, CS, ES, P, D>, StatisticsSource {

    private final CollectiveAStar<SS, CS, ES, D, P> collectiveAStar;
    private final WaveFront<SS, CS, ES, D, P> waveFront;
    private final DeviationZonesFinder<SS, CS, ES, D, P> deviationZonesFinder;
    private Statistics statistics;

    public ASWPlanner(AbstractNumberHandler<D> abstractNumberHandler, DeviationZonesFinder<SS, CS, ES, D, P>
            deviationZonesFinder) {
        this.deviationZonesFinder = Preconditions.checkNotNull(deviationZonesFinder);
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        collectiveAStar = new CollectiveAStar<>(abstractNumberHandler);
        waveFront = new WaveFront<>(abstractNumberHandler);
    }

    @Override
    public OutputPlan<SS, CS, ES, P, D> calculatePlan(InputPlan<SS, CS, ES, P, D> inputPlan) {
        validate(inputPlan);
        statistics = new Statistics("aswPlanner");

        PlanningData<SS, CS, ES, P, D> planningData = new PlanningData<>(inputPlan);

        ImmutableCollectivePath<CS, ES, P> path = collectiveAStar.calculatePath(planningData);
        planningData.setCollectivePath(path);

        Set<DeviationZone<CS, ES, P>> deviationZones = deviationZonesFinder.findDeviationZones(planningData);
        statistics.putStat("deviationZones", deviationZones.size());

        Preconditions.checkNotNull(deviationZones, "DeviationZonesFinder must return a set (can be empty).");
        planningData.setDeviationZones(deviationZones);

        Set<DeviationZonePlan<CS, ES, P>> deviationZonePlans = waveFront.makeDeviationZonePlans(
                deviationZones, inputPlan.getStateSpace());

        return new ASWOutputPlan<>(inputPlan, path, deviationZonePlans);
    }

    private static void validate(InputPlan inputPlan) {
        Preconditions.checkArgument(inputPlan.getInitialCollectiveState().getEntityStates().size() == inputPlan
                .getTargetCollectiveState().getEntityStates().size(), "Initial and target state sizes mismatch");
    }

    @Override public Statistics getStatistics() {
        statistics.mergeWith(collectiveAStar.getStatistics());
        return statistics;
    }
}
