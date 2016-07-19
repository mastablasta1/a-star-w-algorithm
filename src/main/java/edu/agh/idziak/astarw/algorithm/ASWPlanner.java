package edu.agh.idziak.astarw.algorithm;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.*;
import edu.agh.idziak.common.DoubleIterator;
import edu.agh.idziak.common.SingleTypePair;

import java.util.List;
import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ASWPlanner<SS extends StateSpace<U>, S extends GlobalState<U>, U extends Comparable<U>> implements Planner<SS, S, U> {

    private final AStar<SS, S, U> aStar;
    private final DeviationZonesDetector<SS, U> deviationZonesDetector;

    public ASWPlanner(AbstractNumberHandler<U> abstractNumberHandler) {
        Preconditions.checkNotNull(abstractNumberHandler, "Number handler was null");
        aStar = new AStar<>(abstractNumberHandler);
        deviationZonesDetector = new DeviationZonesDetector<>();
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
            SS stateSpace = planningOperation.getInputPlan().getStateSpace();
            ImmutableListPath<U> path = aStar.calculatePath(entityStates.getOne(), entityStates.getTwo(), stateSpace);
            planningOperation.getPaths().add(path);
        }

        Set<DeviationZone<U>> deviationZones = deviationZonesDetector.detectDeviationZones(planningOperation.getPaths(), inputPlan.getStateSpace());

        return new ASWOutputPlan<>(inputPlan.getStateSpace(),
                inputPlan.getInitialGlobalState(),
                inputPlan.getTargetGlobalState(),
                planningOperation.getPaths());
    }
}
