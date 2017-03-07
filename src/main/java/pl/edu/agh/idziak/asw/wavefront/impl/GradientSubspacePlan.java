package pl.edu.agh.idziak.asw.wavefront.impl;

import com.google.common.collect.ImmutableMap;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.ImmutableCollectivePath;
import pl.edu.agh.idziak.asw.model.StateSpace;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomasz on 20.02.2017.
 */
public class GradientSubspacePlan<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>> implements
        SubspacePlan<SS, CS> {

    private Subspace<CS> subspace;
    private Map<CS, D> gradientMap;
    private SS stateSpace;

    private GradientSubspacePlan(Subspace<CS> subspace, Map<CS, D> distancesMap, SS stateSpace) {
        this.subspace = subspace;
        this.gradientMap = ImmutableMap.copyOf(distancesMap);
        this.stateSpace = stateSpace;
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
            if(current==null){
                return null;
            }
        }
        return ImmutableCollectivePath.from(path);
    }

    public Subspace<CS> getSubspace() {
        return subspace;
    }

    public static <SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>>
    GradientSubspacePlan<SS, CS, D> from(Subspace<CS> subspace, Map<CS, D> distancesMap, SS stateSpace) {
        return new GradientSubspacePlan<>(subspace, distancesMap, stateSpace);
    }
}
