package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.CollectiveState;

/**
 * Created by Tomasz on 03/05/2017.
 */
public abstract class AStarStateMonitor<CS extends CollectiveState> {
    public void onIteration(AStarIterationData<CS> AStarIterationData) {
    }

    public void onFinish(int closedSetSize, int openSetSize) {
    }
}
