package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.EntityBasicPlan;
import edu.agh.idziak.astarw.PlannerInput;
import edu.agh.idziak.astarw.StateSpace;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class Grid2DPlannerInput implements PlannerInput<Entity,Point,State,Integer> {
    @Override
    public Set<EntityBasicPlan<Entity, Point>> getEntityBasicPlans() {
        return null;
    }

    @Override
    public StateSpace<State, Point, Integer> getStateSpace() {
        return null;
    }
}
