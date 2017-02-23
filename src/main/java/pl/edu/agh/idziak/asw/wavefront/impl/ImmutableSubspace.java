package pl.edu.agh.idziak.asw.wavefront.impl;


import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 20.02.2017.
 */
public class ImmutableSubspace<CS extends CollectiveState<?, ?>> implements Subspace<CS> {
    private Set<CS> states;
    private CS targetState;

    private ImmutableSubspace() {
    }

    @Override public Set<CS> getStates() {
        return states;
    }

    @Override public CS getTargetState() {
        return targetState;
    }

    public static <CS extends CollectiveState<?, ?>> ImmutableSubspace<CS> from(Set<CS> states, CS
            targetState) {
        ImmutableSubspace<CS> devZone = new ImmutableSubspace<>();
        devZone.states = checkNotNull(states);
        devZone.targetState = checkNotNull(targetState);
        return devZone;
    }
}
