package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.AlgorithmType;
import pl.edu.agh.idziak.asw.impl.BaseWavefrontPlanner;

/**
 * Created by Tomasz on 22.02.2017.
 */
public class GridWavefrontOnlyPlanner extends BaseWavefrontPlanner<GridInputPlan, GridCollectiveStateSpace, GridCollectiveState, Double> {

    public GridWavefrontOnlyPlanner() {
        super(DoubleHandler.getInstance());
    }

    @Override
    public GridASWOutputPlan calculatePlan(GridInputPlan inputPlan) {
        inputPlan.setInitialState(inputPlan.getCollectiveStateSpace().collectiveStateFrom(inputPlan.getInitialCollectiveState().getArray()));
        inputPlan.setGoalState(inputPlan.getCollectiveStateSpace().collectiveStateFrom(inputPlan.getTargetCollectiveState().getArray()));
        return new GridASWOutputPlan(super.calculatePlan(inputPlan), AlgorithmType.WAVEFRONT);
    }
}
