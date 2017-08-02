package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import pl.edu.agh.idziak.asw.model.InputPlan;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class GridInputPlan implements InputPlan<GridStateSpace, GridCollectiveState, Double> {

    private GridCollectiveState initialState;
    private GridCollectiveState targetState;
    private final GridStateSpace globalStateSpace;
    private List<?> entities;
    private GridCostFunction costFunction;

    public GridInputPlan(List<?> entities,
                         GridStateSpace globalStateSpace,
                         GridCollectiveState initialState,
                         GridCollectiveState targetState) {
        this.entities = checkNotNull(entities);
        this.globalStateSpace = checkNotNull(globalStateSpace);
        this.targetState = checkNotNull(targetState);
        this.initialState = checkNotNull(initialState);
        this.costFunction = new GridCostFunction();
        validateInput();
    }

    private void validateInput() {
        Preconditions.checkArgument(entities.size() == initialState.entityStatesCount());
        Preconditions.checkArgument(entities.size() == targetState.entityStatesCount());
    }

    @Override
    public GridCollectiveState getInitialCollectiveState() {
        return initialState;
    }

    @Override
    public GridCollectiveState getTargetCollectiveState() {
        return targetState;
    }

    @Override
    public GridStateSpace getStateSpace() {
        return globalStateSpace;
    }

    @Override
    public List<?> getEntities() {
        return entities;
    }

    void setInitialState(GridCollectiveState initialState) {
        this.initialState = initialState;
    }

    void setTargetState(GridCollectiveState targetState) {
        this.targetState = targetState;
    }


    @Override
    public GridCostFunction getCostFunction() {
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



    public GridEntityState getStateForEntity(GridCollectiveState colState, Object entity) {
        int i = entities.indexOf(entity);
        if (i < 0) {
            return null;
        }
        return GridEntityState.of(colState.getArray()[2 * i], colState.getArray()[2 * i + 1]);
    }
}
