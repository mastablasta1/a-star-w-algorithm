package edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface InputPlan<SS extends StateSpace<CS, ES, D, P>, CS extends CollectiveState<ES, P>, ES extends
        EntityState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    CS getInitialCollectiveState();

    CS getTargetCollectiveState();

    SS getStateSpace();

    Set<?> getEntities();
}
