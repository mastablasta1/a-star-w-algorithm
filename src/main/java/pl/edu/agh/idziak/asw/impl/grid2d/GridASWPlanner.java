package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.AlgorithmType;
import pl.edu.agh.idziak.asw.impl.BaseASWPlanner;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class GridASWPlanner extends BaseASWPlanner<GridInputPlan, GridCollectiveStateSpace, GridCollectiveState, Double> {

    public GridASWPlanner() {
        super(DoubleHandler.getInstance(), new GridDeviationSubspaceLocator());
    }

    @Override
    public GridASWOutputPlan calculatePlan(GridInputPlan inputPlan) {
        internalizeAndValidateStates(inputPlan);
        return new GridASWOutputPlan(super.calculatePlan(inputPlan), AlgorithmType.ASW);
    }

    private void internalizeAndValidateStates(GridInputPlan inputPlan) {
        GridCollectiveState initialState = inputPlan.getCollectiveStateSpace().collectiveStateFrom(inputPlan.getInitialCollectiveState().getArray());
        inputPlan.setInitialState(initialState);
        GridCollectiveState targetState = inputPlan.getCollectiveStateSpace().collectiveStateFrom(inputPlan.getTargetCollectiveState().getArray());
        inputPlan.setTargetState(targetState);
        if (initialState == null || targetState == null) {
            throw new IllegalStateException("Initial or target state is invalid");
        }
    }

    @Override
    public GridDeviationSubspaceLocator getDeviationSubspaceLocator() {
        return (GridDeviationSubspaceLocator) super.getDeviationSubspaceLocator();
    }
}
