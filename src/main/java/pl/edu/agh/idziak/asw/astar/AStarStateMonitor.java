package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.CollectiveState;

/**
 * Created by Tomasz on 03/05/2017.
 */
public interface AStarStateMonitor<CS extends CollectiveState<?>> {
    void onIteration(AStarIterationData<CS> AStarIterationData);

    void onSuccess(int closedSetSize, int openSetSize);
}
