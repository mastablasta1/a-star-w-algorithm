package edu.agh.idziak.asw.logic;

import edu.agh.idziak.asw.CollectivePath;
import edu.agh.idziak.asw.CollectiveState;
import edu.agh.idziak.asw.InputPlan;
import edu.agh.idziak.asw.StateSpace;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class PlanningData<SS extends StateSpace<GS, P, D>, GS extends CollectiveState<P>, P extends Comparable<P>, D extends Comparable<D>> {
    private final InputPlan<SS, GS, P, D> inputPlan;
    private CollectivePath<P> collectivePath;

    PlanningData(InputPlan<SS, GS, P, D> inputPlan) {
        this.inputPlan = inputPlan;
    }

    public InputPlan<SS, GS, P, D> getInputPlan() {
        return inputPlan;
    }

    public CollectivePath<P> getCollectivePath() {
        return collectivePath;
    }

    public void setCollectivePath(CollectivePath<P> collectivePath) {
        this.collectivePath = collectivePath;
    }

}
