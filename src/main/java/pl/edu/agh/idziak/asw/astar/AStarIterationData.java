package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.CollectiveStateSpace;
import pl.edu.agh.idziak.asw.model.InputPlan;

import java.util.Collection;

public class AStarIterationData<CS extends CollectiveState<?>> {
    private final InputPlan<? extends CollectiveStateSpace<CS>, ? extends CS, ?> inputPlan;
    private final DoubleValuePriorityMap.Entry<CS, ?> currentState;
    private final Collection<CS> neighbors;
    private final int openSetSize;
    private final int closedSetSize;

    public AStarIterationData(InputPlan<? extends CollectiveStateSpace<CS>, ? extends CS, ?> inputPlan,
                              DoubleValuePriorityMap.Entry<CS,?> currentStateInfo,
                              Collection<CS> neighbors,
                              int openSetSize,
                              int closedSetSize) {
        this.inputPlan = inputPlan;
        this.currentState = currentStateInfo;
        this.neighbors = neighbors;
        this.openSetSize = openSetSize;
        this.closedSetSize = closedSetSize;
    }


    public InputPlan<? extends CollectiveStateSpace<CS>, ? extends CS, ?> getInputPlan() {
        return inputPlan;
    }

    public DoubleValuePriorityMap.Entry<CS, ?> getCurrentState() {
        return currentState;
    }

    public Collection<CS> getNeighbors() {
        return neighbors;
    }

    public int getOpenSetSize() {
        return openSetSize;
    }

    public int getClosedSetSize() {
        return closedSetSize;
    }
}
