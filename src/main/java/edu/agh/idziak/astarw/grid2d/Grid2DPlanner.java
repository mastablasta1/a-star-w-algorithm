package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.Plan;
import edu.agh.idziak.astarw.Planner;
import edu.agh.idziak.astarw.PlannerInput;
import edu.agh.idziak.astarw.StateSpace;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class Grid2DPlanner implements Planner<Entity, Point, State, Integer> {
    @Override
    public Plan<Entity, Point, State, Integer> calculatePlan(PlannerInput<Entity, Point, State, Integer> plannerInput) {
        StateSpace<State, Point, Integer> stateSpace = plannerInput.getStateSpace();

        return null;
    }
}
