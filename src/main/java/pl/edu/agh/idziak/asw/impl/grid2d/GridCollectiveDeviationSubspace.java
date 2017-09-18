package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GridCollectiveDeviationSubspace implements GridDeviationSubspace {
    private GridCollectiveStateSpace stateSpace;
    private final Set<GridCollectiveState> stateSet;
    private final GridCollectiveState targetState;
    private Set<GridEntityState> containedEntityStates;
    private List<Object> entities;

    public GridCollectiveDeviationSubspace(GridCollectiveStateSpace stateSpace, Set<GridCollectiveState> stateSet, GridCollectiveState targetState, List<?> entities) {
        this.stateSpace = stateSpace;
        this.stateSet = stateSet;
        this.targetState = targetState;
        this.entities = ImmutableList.copyOf(entities);
    }

    @Override
    public Set<GridEntityState> getContainedEntityStates() {
        if (containedEntityStates == null) {
            containedEntityStates = stateSet.stream()
                    .flatMap(gridCollectiveState -> gridCollectiveState.getEntityStates().stream())
                    .collect(Collectors.toSet());
        }
        return containedEntityStates;
    }

    @Override
    public Collection<GridCollectiveState> getNeighborStatesOf(GridCollectiveState colState) {
        List<GridCollectiveState> neighbors = stateSpace.getNeighborStatesOf(colState);
        neighbors.removeIf(s -> !stateSet.contains(s));
        return neighbors;
    }

    public int countCollectiveStates() {
        return stateSet.size();
    }

    public int countEntities() {
        return targetState.getEntityStates().size();
    }

    @Override
    public GridCollectiveState getTargetState() {
        return targetState;
    }

    @Override
    public boolean containsState(GridCollectiveState gridCollectiveState) {
        return stateSet.contains(gridCollectiveState);
    }

    public List<Object> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("target", targetState)
                .toString();
    }
}
