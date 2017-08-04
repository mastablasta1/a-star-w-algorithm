package pl.edu.agh.idziak.asw.model;


import java.util.Collection;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface InputPlan<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>> {
    CS getInitialCollectiveState();

    CS getTargetCollectiveState();

    SS getStateSpace();

    Collection<?> getEntities();

    DistanceHeuristic<CS, D> getDistanceHeuristic();
}
