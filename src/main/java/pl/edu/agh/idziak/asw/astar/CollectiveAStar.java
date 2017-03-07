package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.InputPlan;
import pl.edu.agh.idziak.asw.model.StateSpace;

/**
 * Created by Tomasz on 03.03.2017.
 */
public interface CollectiveAStar<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>> {
    CollectivePath<CS> calculatePath(InputPlan<SS, CS, D> inputPlan);
}
