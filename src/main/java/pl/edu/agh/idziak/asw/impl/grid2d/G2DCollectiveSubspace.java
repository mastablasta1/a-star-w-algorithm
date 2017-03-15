package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 21.08.2016.
 */
public class G2DCollectiveSubspace implements G2DSubspace {

    private Set<G2DCollectiveState> states;
    private G2DCollectiveState targetState;

    public G2DCollectiveSubspace(Set<G2DCollectiveState> states, G2DCollectiveState targetState) {
        if (!states.contains(targetState)) {
            throw new IllegalArgumentException("State set does not contain target state");
        }
        this.states = ImmutableSet.copyOf(states);
        this.targetState = targetState;
    }

    @Override public boolean contains(G2DCollectiveState collectiveState) {
        return states.contains(collectiveState);
    }

    @Override
    public G2DCollectiveState getTargetState() {
        return targetState;
    }

    @Override public Set<G2DEntityState> getContainedEntityStates() {
        return states.stream()
                     .flatMap(collectiveState -> collectiveState.getEntityStates().values().stream())
                     .distinct()
                     .collect(Collectors.toSet());
    }
}
