package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseASWPlanner;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class G2DPlanner extends BaseASWPlanner<G2DInputPlan, G2DStateSpace, G2DCollectiveState, Double> {

    private static final DoubleHandler DOUBLE_HANDLER = DoubleHandler.getInstance();
    private static final G2DDeviationZonesFinder DEVIATION_ZONES_FINDER = new G2DDeviationZonesFinder(2);

    public G2DPlanner() {
        super(DOUBLE_HANDLER, DEVIATION_ZONES_FINDER);
    }
}
