package pl.edu.agh.idziak.asw.wavefront.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;

import java.util.*;

/**
 * Created by Tomasz on 20.02.2017.
 */
public class GradientSubspacePlan<SS extends StateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>>
        implements SubspacePlan<CS> {

    private final Set<?> entities;
    private final Subspace<CS> subspace;
    private final Map<CS, D> gradientMap;
    private final SS stateSpace;

    private GradientSubspacePlan(Subspace<CS> subspace, Map<CS, D> distancesMap, SS stateSpace) {
        this.subspace = subspace;
        this.gradientMap = ImmutableMap.copyOf(distancesMap);
        this.stateSpace = stateSpace;
        this.entities = ImmutableSet.of();
    }

    @Override
    public CS getNextMove(CS collectiveState) {
        return stateSpace.getNeighborStatesOf(collectiveState)
                         .stream()
                         .min(Comparator.comparing(o -> gradientMap.get(o)))
                         .orElse(null);
    }

    @Override public CollectivePath<CS> constructPath(CS start, CS goal) {
        CS current = start;
        List<CS> path = new LinkedList<>();
        path.add(current);
        while (!current.equals(goal)) {
            current = getNextMove(current);
            path.add(current);
            if (current == null) {
                return null;
            }
        }
        return ImmutableCollectivePath.from(path);
    }

    public Subspace<CS> getSubspace() {
        return subspace;
    }

    @Override public Set<?> getEntities() {
        return entities;
    }

    public static <SS extends StateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>>
    GradientSubspacePlan<SS, CS, D> from(Subspace<CS> subspace, Map<CS, D> distancesMap, SS stateSpace) {
        return new GradientSubspacePlan<>(subspace, distancesMap, stateSpace);
    }
}
