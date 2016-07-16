package edu.agh.idziak.astarw.algorithm;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.*;
import edu.agh.idziak.common.DoubleIterator;
import edu.agh.idziak.common.SingleTypePair;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<U>, S extends GlobalState<U>, U extends Comparable<U>> implements Planner<SS, S, U> {

    private final AStar<SS, S, U> aStar;

    public ASWPlanner(AbstractNumberHandler<U> abstractNumberHandler) {
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        aStar = new AStar<>(abstractNumberHandler);
    }

    private void validate(InputPlan<SS, S, U> inputPlan) {
        Preconditions.checkArgument(inputPlan.getTargetGlobalState().getSize() == inputPlan.getInitialGlobalState().getSize(), "Initial and target state sizes mismatch");
    }

    @Override
    public OutputPlan<SS, S, U> calculatePlan(InputPlan<SS, S, U> inputPlan) {
        validate(inputPlan);

        PlanningOperation<SS, S, U> planningOperation = new PlanningOperation<>(inputPlan);

        List<EntityState<U>> initialEntityStates = inputPlan.getInitialGlobalState().getEntityStates();
        List<EntityState<U>> targetEntityStates = inputPlan.getTargetGlobalState().getEntityStates();

        DoubleIterator<EntityState<U>> it = new DoubleIterator<>(initialEntityStates, targetEntityStates);

        while (it.hasNext()) {
            SingleTypePair<EntityState<U>> entityStates = it.next();
            ImmutableListPath<U> path = aStar.calculatePath(entityStates.getOne(), entityStates.getTwo(), planningOperation);
            planningOperation.getPaths().add(path);
        }

        return new ASWOutputPlan<>(inputPlan.getStateSpace(),
                inputPlan.getInitialGlobalState(),
                inputPlan.getTargetGlobalState(),
                planningOperation.getPaths());
    }
}
