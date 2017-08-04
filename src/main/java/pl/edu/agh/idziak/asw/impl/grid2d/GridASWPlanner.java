package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseASWPlanner;
import pl.edu.agh.idziak.asw.model.ASWOutputPlan;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class GridASWPlanner extends BaseASWPlanner<GridInputPlan, GridCollectiveStateSpace, GridCollectiveState, Double> {

    public GridASWPlanner() {
        super(DoubleHandler.getInstance(), new GridDeviationSubspaceLocator());
    }

    @Override
    public ASWOutputPlan<GridCollectiveStateSpace, GridCollectiveState> calculatePlan(GridInputPlan inputPlan) {
        GridCollectiveState initialState = inputPlan.getStateSpace().collectiveStateFrom(inputPlan.getInitialCollectiveState().getArray());
        inputPlan.setInitialState(initialState);
        GridCollectiveState targetState = inputPlan.getStateSpace().collectiveStateFrom(inputPlan.getTargetCollectiveState().getArray());
        inputPlan.setTargetState(targetState);
        if (initialState == null || targetState == null) {
            throw new IllegalStateException("Initial or target state is invalid");
        }
        return super.calculatePlan(inputPlan);
    }
}