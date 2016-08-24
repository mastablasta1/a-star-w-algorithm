package edu.agh.idziak.asw;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface DeviationZonePlan<CS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>> {
    DeviationZone<CS, ES, P> getDeviationZone();

    CS getNextMove(CS collectiveState);
}
