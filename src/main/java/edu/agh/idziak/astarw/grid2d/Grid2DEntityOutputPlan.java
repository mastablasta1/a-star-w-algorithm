package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.algorithm.BasicEntityOutputPlan;

import java.util.List;

/**
 * Created by Tomasz on 31.07.2016.
 */
public class Grid2DEntityOutputPlan<P extends Comparable<P>> extends BasicEntityOutputPlan<P> {
    public Grid2DEntityOutputPlan(List<DeviationZone<P>> deviationZones, List<EntityState<P>> path) {
        super(deviationZones, path);
    }

    @Override
    public DeviationZone<P> getApplicableDeviationZone(EntityState<P> entityState) {
        return null;
    }
}
