package edu.agh.idziak.asw.grid2d;

import edu.agh.idziak.asw.InputPlan;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DInputPlan implements InputPlan<G2DStateSpace, G2DCollectiveState, Integer, Double> {

    private final G2DCollectiveState initialState;
    private final G2DCollectiveState targetState;
    private final G2DStateSpace globalStateSpace;
    private Set<?> entities;

    public G2DInputPlan(Set entities,
                        G2DStateSpace globalStateSpace,
                        G2DCollectiveState initialState,
                        G2DCollectiveState targetState) {
        this.entities = entities;
        this.globalStateSpace = globalStateSpace;
        this.targetState = targetState;
        this.initialState = initialState;
    }

    @Override
    public G2DCollectiveState getInitialGlobalState() {
        return initialState;
    }

    @Override
    public G2DCollectiveState getTargetGlobalState() {
        return targetState;
    }

    @Override
    public G2DStateSpace getStateSpace() {
        return globalStateSpace;
    }

    @Override
    public Set<?> getEntities() {
        return entities;
    }

}
