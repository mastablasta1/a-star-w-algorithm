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

    private List<?> entities;
    private GridCollectiveState initialState;
    private GridCollectiveState goalState;
    private final GridCollectiveStateSpace collectiveStateSpace;
    private final NeighborhoodType neighborhoodType;
    private final GridDistanceHeuristic distanceHeuristic;

    public GridInputPlan(List<?> entities,
                         GridCollectiveStateSpace collectiveStateSpace,
                         GridCollectiveState initialState,
                         GridCollectiveState goalState,
                         NeighborhoodType neighborhoodType) {
        this.entities = checkNotNull(entities);
        this.collectiveStateSpace = checkNotNull(collectiveStateSpace);
        this.goalState = checkNotNull(goalState);
        this.initialState = checkNotNull(initialState);
        this.neighborhoodType = checkNotNull(neighborhoodType);

        this.collectiveStateSpace.setNeighborhood(this.neighborhoodType);
        this.distanceHeuristic = new GridDistanceHeuristic(this.neighborhoodType);
        this.distanceHeuristic.setGoal(goalState);

        checkArgument(entities.size() == initialState.entityStatesCount(),
                "Initial state length does not match number of entities");
        checkArgument(entities.size() == goalState.entityStatesCount(),
                "Target state length does not match number of entities");
    }

    private GridInputPlan(Builder builder) {
        this(builder.entities,
                builder.collectiveStateSpace,
                builder.initialState,
                builder.goalState,
                builder.neighborhoodType);
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
        return goalState;
    }

    @Override
    public GridCollectiveStateSpace getCollectiveStateSpace() {
        return collectiveStateSpace;
    }

    @Override
    public List<?> getEntities() {
        return entities;
    }

    void setInitialState(GridCollectiveState initialState) {
        this.initialState = initialState;
    }

    void setGoalState(GridCollectiveState goalState) {
        this.goalState = goalState;
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
                .add("goalState", goalState)
                .add("collectiveStateSpace", collectiveStateSpace)
                .toString();
    }


    public GridEntityState getStateForEntity(GridCollectiveState colState, Object entity) {
        int i = entities.indexOf(entity);
        if (i < 0) {
            return null;
        }
        return GridEntityState.of(colState.getArray()[2 * i], colState.getArray()[2 * i + 1]);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<?> entities;
        private GridCollectiveState initialState;
        private GridCollectiveState goalState;
        private GridCollectiveStateSpace collectiveStateSpace;
        private NeighborhoodType neighborhoodType;

        public Builder() {
        }

        public Builder entities(List<?> val) {
            entities = val;
            return this;
        }

        public Builder initialState(GridCollectiveState val) {
            initialState = val;
            return this;
        }

        public Builder goalState(GridCollectiveState val) {
            goalState = val;
            return this;
        }

        public Builder collectiveStateSpace(GridCollectiveStateSpace val) {
            collectiveStateSpace = val;
            return this;
        }

        public Builder neighborhoodType(NeighborhoodType val) {
            neighborhoodType = val;
            return this;
        }

        public GridInputPlan build() {
            return new GridInputPlan(this);
        }
    }
}
