package edu.agh.idziak.astarw.grid2d;

import com.google.common.base.Preconditions;
import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.GlobalPath;
import edu.agh.idziak.astarw.GlobalState;
import edu.agh.idziak.astarw.StateSpace;
import edu.agh.idziak.astarw.algorithm.DeviationZonesDetector;
import edu.agh.idziak.astarw.algorithm.PlanningData;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tomasz on 18.07.2016.
 */
public class Grid2DDeviationZonesDetector implements DeviationZonesDetector<Grid2DStateSpace, Grid2DGlobalState, Integer, Double> {

    private Class<D> distanceClass;
    private int minSafeDistance;

    public Grid2DDeviationZonesDetector(int minSafeDistance) {
        this.distanceClass = Preconditions.checkNotNull(distanceClass);
        this.minSafeDistance = minSafeDistance;
    }

    @Override
    public List<DeviationZone<Integer>> detectDeviationZones(PlanningData<Grid2DStateSpace, Grid2DGlobalState, Integer, Double> planningData) {
        GlobalPath<Integer> path = planningData.getPath();
        Grid2DStateSpace stateSpace = planningData.getInputPlan().getStateSpace();

        for (GlobalState<Integer> state : path.get()) {
            List<EntityState<Integer>> entityStates = state.getEntityStates();
            int size = entityStates.size();

        }

        return null;
    }
}
