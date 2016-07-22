package edu.agh.idziak.astarw.algorithm;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<GS, U>, GS extends GlobalState<U>, U extends Comparable<U>> implements Planner<SS, GS, U> {

    private final GlobalAStar<SS, GS, U> globalAStar;
    private final DeviationZonesDetector<SS, GS, U> entityDeviationZonesDetector;

    public ASWPlanner(AbstractNumberHandler<U> abstractNumberHandler) {
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        globalAStar = new GlobalAStar<>(abstractNumberHandler);
        entityDeviationZonesDetector = new DeviationZonesDetector<>(1);
    }

    private void validate(InputPlan<SS, GS, U> inputPlan) {
        Preconditions.checkArgument(inputPlan.getTargetGlobalState().getEntitiesCount() == inputPlan.getInitialGlobalState().getEntitiesCount(), "Initial and target state sizes mismatch");
    }

    @Override
    public OutputPlan<SS, GS, U> calculatePlan(InputPlan<SS, GS, U> inputPlan) {
        validate(inputPlan);

        PlanningData<SS, GS, U> planningData = new PlanningData<>(inputPlan);

        globalAStar.calculatePath(planningData);

        //entityDeviationZonesDetector.detectDeviationZones(planningData);

        return new ASWOutputPlan<>(inputPlan.getStateSpace(),
                inputPlan.getInitialGlobalState(),
                inputPlan.getTargetGlobalState(),
                planningData.getPath());
    }
}
