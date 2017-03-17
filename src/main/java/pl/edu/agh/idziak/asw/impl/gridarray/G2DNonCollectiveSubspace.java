package pl.edu.agh.idziak.asw.impl.gridarray;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Created by Tomasz on 21.08.2016.
 */
public class G2DNonCollectiveSubspace implements G2DSubspace {

    private Set<G2DEntityState> states;
    private G2DCollectiveState targetState;

    public G2DNonCollectiveSubspace(Set<G2DEntityState> states, G2DCollectiveState targetState) {
        if (!states.containsAll(targetState.getEntityStates().values())) {
            throw new IllegalArgumentException("States set does not contain all target states");
        }
        this.states = ImmutableSet.copyOf(states);
        this.targetState = targetState;
    }

    @Override
    public boolean contains(G2DCollectiveState collectiveState) {
        return states.containsAll(collectiveState.getEntityStates().values());
    }

    public G2DCollectiveState getTargetState() {
        return targetState;
    }

    @Override public Set<G2DEntityState> getContainedEntityStates() {
        return states;
    }
}
