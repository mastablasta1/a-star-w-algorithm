package pl.edu.agh.idziak.asw.impl.gridarray;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseASWPlanner;
import pl.edu.agh.idziak.asw.model.DeviationZonesFinder;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class G2DPlanner extends BaseASWPlanner<G2DInputPlan, G2DStateSpace, G2DCollectiveState, Double> {

    private static final DoubleHandler DOUBLE_HANDLER = DoubleHandler.getInstance();
    private static final DeviationZonesFinder<G2DInputPlan, G2DCollectiveState> DEVIATION_ZONES_FINDER = new G2DNonCollectiveDevZonesFinder();

    public G2DPlanner() {
        super(DOUBLE_HANDLER, DEVIATION_ZONES_FINDER);
    }
}
