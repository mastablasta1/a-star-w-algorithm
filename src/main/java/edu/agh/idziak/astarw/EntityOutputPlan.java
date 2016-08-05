package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 31.07.2016.
 */
public interface EntityOutputPlan<P extends Comparable<P>> {
    List<DeviationZone<P>> getDeviationZones();

    DeviationZone<P> getApplicableDeviationZone(EntityState<P> entityState);

    List<EntityState<P>> getPath();
}
