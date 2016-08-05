package edu.agh.idziak.astarw.algorithm;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.*;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> implements Planner<SS, GS, P, D> {

    private final GlobalAStar<SS, GS, P, D> globalAStar;
    private final DeviationZonesDetector<SS, GS, P, D> deviationZonesDetector;

    public ASWPlanner(AbstractNumberHandler<D> abstractNumberHandler, DeviationZonesDetector<SS, GS, P, D> deviationZonesDetector) {
        this.deviationZonesDetector = Preconditions.checkNotNull(deviationZonesDetector);
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        globalAStar = new GlobalAStar<>(abstractNumberHandler);
    }

    private void validate(InputPlan<SS, GS, P, D> inputPlan) {
        Preconditions.checkArgument(inputPlan.getTargetGlobalState().getEntitiesCount() == inputPlan.getInitialGlobalState().getEntitiesCount(), "Initial and target state sizes mismatch");
    }

    @Override
    public GlobalOutputPlan<SS, GS, P, D> calculatePlan(InputPlan<SS, GS, P, D> inputPlan) {
        validate(inputPlan);

        PlanningData<SS, GS, P, D> planningData = new PlanningData<>(inputPlan);

        globalAStar.calculatePath(planningData);

        List<EntityOutputPlan<P>> entityOutputPlans = deviationZonesDetector.detectDeviationZones(planningData);

        return ASWGlobalOutputPlan.<SS, GS, P, D>builder()
                .entityOutputPlans(entityOutputPlans)
                .initialState(inputPlan.getInitialGlobalState())
                .targetState(inputPlan.getTargetGlobalState())
                .stateSpace(inputPlan.getStateSpace())
                .path(planningData.getGlobalPath())
                .build();
    }
}
