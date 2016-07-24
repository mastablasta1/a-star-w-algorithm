package edu.agh.idziak.astarw.algorithm;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> implements Planner<SS, GS, P, D> {

    private final GlobalAStar<SS, GS, P, D> globalAStar;
    private final DeviationZonesDetector<SS, GS, P, D> entityDeviationZonesDetector;

    public ASWPlanner(AbstractNumberHandler<D> abstractNumberHandler) {
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        globalAStar = new GlobalAStar<>(abstractNumberHandler);
        entityDeviationZonesDetector = new DeviationZonesDetector<>(1);
    }

    private void validate(InputPlan<SS, GS, P, D> inputPlan) {
        Preconditions.checkArgument(inputPlan.getTargetGlobalState().getEntitiesCount() == inputPlan.getInitialGlobalState().getEntitiesCount(), "Initial and target state sizes mismatch");
    }

    @Override
    public OutputPlan<SS, GS, P, D> calculatePlan(InputPlan<SS, GS, P, D> inputPlan) {
        validate(inputPlan);

        PlanningData<SS, GS, P, D> planningData = new PlanningData<>(inputPlan);

        globalAStar.calculatePath(planningData);

        //entityDeviationZonesDetector.detectDeviationZones(planningData);

        return new ASWOutputPlan<>(inputPlan.getStateSpace(),
                inputPlan.getInitialGlobalState(),
                inputPlan.getTargetGlobalState(),
                planningData.getPath());
    }
}
