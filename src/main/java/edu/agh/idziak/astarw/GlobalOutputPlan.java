package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface GlobalOutputPlan<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> extends InputPlan<SS, GS, P, D> {

    GlobalPath<P> getGlobalPath();

    List<EntityOutputPlan<P>> getPlansForEntities();
}
