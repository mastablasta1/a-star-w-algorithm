package edu.agh.idziak.asw;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface DeviationZonePlan<P extends Comparable<P>> {
    DeviationZone<P> getDeviationZone();

    EntityState<P> getBestMoveFrom(EntityState<P> entityState, Object entity);
}
