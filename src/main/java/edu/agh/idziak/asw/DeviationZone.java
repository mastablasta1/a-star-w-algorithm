package edu.agh.idziak.asw;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface DeviationZone<CS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>> {
    Set<ES> getStates();

    CS getTargetState();
}
