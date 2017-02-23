package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.Set;

/**
 * Created by Tomasz on 21.08.2016.
 */
public class G2DSubspace implements Subspace<G2DCollectiveState> {

    private Set<G2DCollectiveState> states;
    private G2DCollectiveState targetState;

    public G2DSubspace(Set<G2DCollectiveState> states, G2DCollectiveState targetState) {
        this.states = ImmutableSet.copyOf(states);
        this.targetState = targetState;
    }

    @Override
    public Set<G2DCollectiveState> getStates() {
        return states;
    }

    @Override
    public G2DCollectiveState getTargetState() {
        return targetState;
    }
}
