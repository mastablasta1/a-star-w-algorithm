package pl.edu.agh.idziak.asw.wavefront;


import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Subspace<CS extends CollectiveState<?, ?>> {
    Set<CS> getStates();

    CS getTargetState();
}
