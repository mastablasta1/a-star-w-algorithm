package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Collection;

/**
 * Created by Tomasz on 03/05/2017.
 */
public interface CurrentStateMonitor<CS extends CollectiveState<?>> {
    void accept(CS currentState, Collection<CS> neighbors, int openSetSize, int closedSetSize);
}
