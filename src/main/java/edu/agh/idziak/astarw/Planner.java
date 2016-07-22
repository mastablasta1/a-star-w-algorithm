package edu.agh.idziak.astarw;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Planner<SS extends StateSpace<GS, U>, GS extends GlobalState<U>, U extends Comparable<U>> {

    OutputPlan<SS, GS, U> calculatePlan(InputPlan<SS, GS, U> inputPlan);
}