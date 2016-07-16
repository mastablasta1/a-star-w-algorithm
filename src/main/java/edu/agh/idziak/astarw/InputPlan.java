package edu.agh.idziak.astarw;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface InputPlan<SS extends StateSpace<U>, GS extends GlobalState<U>, U extends Comparable<U>> {
    GS getInitialGlobalState();

    GS getTargetGlobalState();

    SS getStateSpace();
}
