package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.DeviationZone;
import edu.agh.idziak.asw.DeviationZonesFinder;
import edu.agh.idziak.asw.impl.PlanningData;

import java.util.Set;

/**
 * Created by Tomasz on 14.08.2016.
 */
public class Grid2DDeviationZonesFinder implements DeviationZonesFinder<Grid2DStateSpace,Grid2DCollectiveState,Integer,Double> {
    @Override
    public Set<DeviationZone<Integer>> findDeviationZones(PlanningData<Grid2DStateSpace, Grid2DCollectiveState, Integer, Double> planningData) {
        return null;
    }
}
