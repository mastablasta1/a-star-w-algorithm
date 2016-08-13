package edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface InputPlan<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    GS getInitialGlobalState();

    GS getTargetGlobalState();

    SS getStateSpace();

    Set<?> getEntities();
}
