package pl.edu.agh.idziak.asw.impl.gridarray;

import com.google.common.base.MoreObjects;
import pl.edu.agh.idziak.asw.model.InputPlan;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DOptInputPlan implements InputPlan<G2DOptStateSpace, G2DOptCollectiveState, Double> {

    private final G2DOptCollectiveState initialState;
    private final G2DOptCollectiveState targetState;
    private final G2DOptStateSpace globalStateSpace;
    private List<?> entities;
    private G2DCostFunction costFunction;

    public G2DOptInputPlan(List<?> entities,
                           G2DOptStateSpace globalStateSpace,
                           G2DOptCollectiveState initialState,
                           G2DOptCollectiveState targetState) {
        this.entities = entities;
        this.globalStateSpace = globalStateSpace;
        this.targetState = targetState;
        this.initialState = initialState;
        this.costFunction = new G2DCostFunction();
    }

    @Override
    public G2DOptCollectiveState getInitialCollectiveState() {
        return initialState;
    }

    @Override
    public G2DOptCollectiveState getTargetCollectiveState() {
        return targetState;
    }

    @Override
    public G2DOptStateSpace getStateSpace() {
        return globalStateSpace;
    }

    @Override
    public List<?> getEntities() {
        return entities;
    }

    @Override
    public G2DCostFunction getCostFunction() {
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
