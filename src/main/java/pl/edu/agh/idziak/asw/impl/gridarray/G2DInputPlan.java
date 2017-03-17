package pl.edu.agh.idziak.asw.impl.gridarray;

import com.google.common.base.MoreObjects;
import pl.edu.agh.idziak.asw.model.InputPlan;

import java.util.Set;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DInputPlan implements InputPlan<G2DStateSpace, G2DCollectiveState, Double> {

    private final G2DCollectiveState initialState;
    private final G2DCollectiveState targetState;
    private final G2DStateSpace globalStateSpace;
    private Set<?> entities;
    private G2DCostFunction costFunction;

    public G2DInputPlan(Set entities,
                        G2DStateSpace globalStateSpace,
                        G2DCollectiveState initialState,
                        G2DCollectiveState targetState) {
        this.entities = entities;
        this.globalStateSpace = globalStateSpace;
        this.targetState = targetState;
        this.initialState = initialState;
        this.costFunction = new G2DCostFunction();
    }

    @Override
    public G2DCollectiveState getInitialCollectiveState() {
        return initialState;
    }

    @Override
    public G2DCollectiveState getTargetCollectiveState() {
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

    @Override public G2DCostFunction getCostFunction() {
        return costFunction;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("entities", entities)
                          .add("initialState", initialState)
                          .add("targetState", targetState)
                          .add("globalStateSpace", globalStateSpace)
                          .toString();
    }
}
