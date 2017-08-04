package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.base.MoreObjects;
import pl.edu.agh.idziak.asw.model.InputPlan;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class GridInputPlan implements InputPlan<GridCollectiveStateSpace, GridCollectiveState, Double> {

    private final NeighborhoodType neighborhoodType;
    private GridCollectiveState initialState;
    private GridCollectiveState targetState;
    private final GridCollectiveStateSpace globalStateSpace;
    private List<?> entities;
    private GridDistanceHeuristic distanceHeuristic;

    public GridInputPlan(List<?> entities,
                         GridCollectiveStateSpace globalStateSpace,
                         GridCollectiveState initialState,
                         GridCollectiveState targetState,
                         NeighborhoodType neighborhoodType) {
        this.entities = checkNotNull(entities);
        this.globalStateSpace = checkNotNull(globalStateSpace);
        this.targetState = checkNotNull(targetState);
        this.initialState = checkNotNull(initialState);
        this.neighborhoodType = checkNotNull(neighborhoodType);
        this.globalStateSpace.setNeighborhood(this.neighborhoodType);
        this.distanceHeuristic = new GridDistanceHeuristic(this.neighborhoodType);

        checkArgument(entities.size() == initialState.entityStatesCount());
        checkArgument(entities.size() == targetState.entityStatesCount());
    }

    public NeighborhoodType getNeighborhoodType() {
        return neighborhoodType;
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
    public GridCollectiveStateSpace getStateSpace() {
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
    public GridDistanceHeuristic getDistanceHeuristic() {
        return distanceHeuristic;
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
