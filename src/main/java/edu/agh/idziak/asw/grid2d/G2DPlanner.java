package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.impl.ASWPlanner;
import edu.agh.idziak.common.DoubleHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class G2DPlanner extends ASWPlanner<G2DStateSpace, G2DCollectiveState, G2DEntityState, Double, Integer> {

    private static final DoubleHandler DOUBLE_HANDLER = DoubleHandler.getInstance();
    public static final G2DDeviationZonesFinder DEVIATION_ZONES_FINDER = new G2DDeviationZonesFinder(2);

    public G2DPlanner() {
        super(DOUBLE_HANDLER, DEVIATION_ZONES_FINDER);
    }
}
