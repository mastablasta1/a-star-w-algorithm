package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseWavefrontPlanner;
import pl.edu.agh.idziak.asw.model.ASWOutputPlan;

/**
 * Created by Tomasz on 22.02.2017.
 */
public class GridWavefrontOnlyPlanner extends BaseWavefrontPlanner<GridInputPlan, GridStateSpace, GridCollectiveState, Double> {

    public GridWavefrontOnlyPlanner() {
        super(DoubleHandler.getInstance());
    }

    @Override
    public ASWOutputPlan<GridStateSpace, GridCollectiveState> calculatePlan(GridInputPlan inputPlan) {
        inputPlan.setInitialState(inputPlan.getStateSpace().collectiveStateFrom(inputPlan.getInitialCollectiveState().getArray()));
        inputPlan.setTargetState(inputPlan.getStateSpace().collectiveStateFrom(inputPlan.getTargetCollectiveState().getArray()));
        return super.calculatePlan(inputPlan);
    }
}
