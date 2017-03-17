package pl.edu.agh.idziak.asw.impl.gridarray;

import pl.edu.agh.idziak.asw.common.DoubleHandler;
import pl.edu.agh.idziak.asw.impl.BaseAStarPlanner;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class G2DAStarPlanner extends BaseAStarPlanner<G2DInputPlan, G2DStateSpace, G2DCollectiveState, Double> {

    private static final DoubleHandler DOUBLE_HANDLER = DoubleHandler.getInstance();

    public G2DAStarPlanner() {
        super(DOUBLE_HANDLER);
    }
}
