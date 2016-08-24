package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.DeviationZone;

import java.util.Set;

/**
 * Created by Tomasz on 21.08.2016.
 */
public class G2DDeviationZone implements DeviationZone<G2DCollectiveState, G2DEntityState, Integer> {

    private Set<G2DEntityState> states;
    private G2DCollectiveState targetState;

    public G2DDeviationZone(Set<G2DEntityState> states, G2DCollectiveState targetState) {
        this.states = states;
        this.targetState = targetState;
    }

    @Override
    public Set<G2DEntityState> getStates() {
        return states;
    }

    @Override
    public G2DCollectiveState getTargetState() {
        return targetState;
    }
}
