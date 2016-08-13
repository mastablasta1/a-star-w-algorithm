package edu.agh.idziak.astarw.algorithm;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.*;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> implements Planner<SS, GS, P, D> {

    private final CollectiveAStar<SS, GS, P, D> collectiveAStar;

    public ASWPlanner(AbstractNumberHandler<D> abstractNumberHandler) {
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        collectiveAStar = new CollectiveAStar<>(abstractNumberHandler);
    }

    private void validate(InputPlan inputPlan) {
        Preconditions.checkArgument(inputPlan.getInitialGlobalState().getEntityStates().size() == inputPlan.getTargetGlobalState().getEntityStates().size(), "Initial and target state sizes mismatch");
    }

    @Override
    public OutputPlan<SS, GS, P, D> calculatePlan(InputPlan<SS, GS, P, D> inputPlan) {
        validate(inputPlan);

        PlanningData<SS, GS, P, D> planningData = new PlanningData<>(inputPlan);

        ImmutableCollectivePath<P> path = collectiveAStar.calculatePath(planningData);



        return new ASWOutputPlan<>(inputPlan, path, null);
    }
}
