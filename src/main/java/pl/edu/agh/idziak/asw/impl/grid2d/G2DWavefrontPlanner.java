package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseWavefrontPlanner;

/**
 * Created by Tomasz on 22.02.2017.
 */
public class G2DWavefrontPlanner extends BaseWavefrontPlanner<G2DInputPlan, G2DStateSpace, G2DCollectiveState, Double> {

    public G2DWavefrontPlanner() {
        super(DoubleHandler.getInstance());
    }
}
