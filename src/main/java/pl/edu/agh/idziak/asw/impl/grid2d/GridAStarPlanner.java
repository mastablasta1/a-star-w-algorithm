package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.AlgorithmType;
import pl.edu.agh.idziak.asw.impl.BaseAStarPlanner;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class GridAStarPlanner extends BaseAStarPlanner<GridInputPlan, GridCollectiveStateSpace, GridCollectiveState, Double> {

    public GridAStarPlanner() {
        super(DoubleHandler.getInstance());
    }

    @Override
    public GridASWOutputPlan calculatePlan(GridInputPlan inputPlan) {
        GridCollectiveState initialState = inputPlan.getCollectiveStateSpace().collectiveStateFrom(inputPlan.getInitialCollectiveState().getArray());
        inputPlan.setInitialState(initialState);
        GridCollectiveState targetState = inputPlan.getCollectiveStateSpace().collectiveStateFrom(inputPlan.getTargetCollectiveState().getArray());
        inputPlan.setTargetState(targetState);
        if (initialState == null || targetState == null) {
            throw new IllegalStateException("Initial or target state is invalid");
        }
        return new GridASWOutputPlan(super.calculatePlan(inputPlan), AlgorithmType.ASTAR_ONLY);
    }
}
