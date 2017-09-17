package pl.edu.agh.idziak.asw.wavefront;


import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Collection;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface DeviationSubspace<CS extends CollectiveState> {
    Collection<CS> getNeighborStatesOf(CS globalState);
    CS getTargetState();
    boolean containsState(CS cs);
}
