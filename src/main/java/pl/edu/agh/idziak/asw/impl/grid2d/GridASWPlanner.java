package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseASWPlanner;
import pl.edu.agh.idziak.asw.model.ASWOutputPlan;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class GridASWPlanner extends BaseASWPlanner<GridInputPlan, GridStateSpace, GridCollectiveState, Double> {

    public GridASWPlanner() {
        super(DoubleHandler.getInstance(), new GridDeviationSubspaceLocator());
    }

    @Override
    public ASWOutputPlan<GridStateSpace, GridCollectiveState> calculatePlan(GridInputPlan inputPlan) {
        inputPlan.setInitialState(inputPlan.getStateSpace().collectiveStateFrom(inputPlan.getInitialCollectiveState().getArray()));
        inputPlan.setTargetState(inputPlan.getStateSpace().collectiveStateFrom(inputPlan.getTargetCollectiveState().getArray()));
        return super.calculatePlan(inputPlan);
    }
}
