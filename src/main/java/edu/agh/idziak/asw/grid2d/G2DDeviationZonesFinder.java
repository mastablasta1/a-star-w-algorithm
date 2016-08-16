package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.DeviationZone;
import edu.agh.idziak.asw.DeviationZonesFinder;
import edu.agh.idziak.asw.impl.PlanningData;

import java.util.Set;

/**
 * Created by Tomasz on 14.08.2016.
 */
public class G2DDeviationZonesFinder implements DeviationZonesFinder<G2DStateSpace, G2DCollectiveState, G2DEntityState, Double, Integer> {
    @Override
    public Set<DeviationZone<Integer>> findDeviationZones(PlanningData<G2DStateSpace, G2DCollectiveState, G2DEntityState, Integer, Double> planningData) {
        return null;
    }
}
