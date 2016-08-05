package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.GlobalPath;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.InputPlan;
import edu.agh.idziak.astarw.StateSpace;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class PlanningData<SS extends StateSpace<GS, P, D>, GS extends GlobalState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    private final InputPlan<SS, GS, P, D> inputPlan;
    private GlobalPath<P> globalPath;
    private List<MutableEntityOutputPlan<P>> mutableEntityOutputPlan;

    PlanningData(InputPlan<SS, GS, P, D> inputPlan) {
        this.inputPlan = inputPlan;
    }

    public InputPlan<SS, GS, P, D> getInputPlan() {
        return inputPlan;
    }

    public GlobalPath<P> getGlobalPath() {
        return globalPath;
    }

    public void setGlobalPath(GlobalPath<P> globalPath) {
        this.globalPath = globalPath;
    }

}
