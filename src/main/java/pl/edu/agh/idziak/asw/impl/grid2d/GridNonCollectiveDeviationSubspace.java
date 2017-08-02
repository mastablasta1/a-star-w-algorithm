package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 21.08.2016.
 */
public class GridNonCollectiveDeviationSubspace implements GridDeviationSubspace {
    private List<?> entities;
    private GridCollectiveState targetState;
    private Set<GridEntityState> containedStates;
    private GridStateSpace stateSpace;

    public GridNonCollectiveDeviationSubspace(Set<GridEntityState> containedStates,
                                              GridStateSpace stateSpace,
                                              List<GridEntityState> targetState,
                                              List<?> entities) {
        this.containedStates = containedStates;
        this.stateSpace = stateSpace;
        this.entities = ImmutableList.copyOf(entities);
        this.targetState = stateSpace.collectiveStateFrom(targetState);
        Preconditions.checkArgument(this.targetState != null, "Input target state is invalid");
    }

    @Override
    public Collection<GridCollectiveState> getNeighborStatesOf(GridCollectiveState collectiveState) {
        return stateSpace.getNeighborStatesOf(collectiveState)
                .stream()
                .filter(colState -> containedStates.containsAll(colState.getEntityStates()))
                .collect(Collectors.toList());
    }

    @Override
    public GridCollectiveState getTargetState() {
        return targetState;
    }

    @Override
    public Set<GridEntityState> getContainedEntityStates() {
        return containedStates;
    }
}
