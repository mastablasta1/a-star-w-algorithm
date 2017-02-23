package pl.edu.agh.idziak.asw.wavefront.impl;

import com.google.common.collect.ImmutableMap;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.StateSpace;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Tomasz on 20.02.2017.
 */
public class GradientSubspacePlan<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>> implements
        SubspacePlan<SS, CS> {

    private Subspace<CS> subspace;
    private Map<CS, D> gradientMap;

    private GradientSubspacePlan(Subspace<CS> subspace, Map<CS, D> distancesMap) {
        this.subspace = subspace;
        this.gradientMap = ImmutableMap.copyOf(distancesMap);
    }

    @Override
    public CS getNextMove(CS collectiveState, SS stateSpace) {
        return stateSpace.getNeighborStatesOf(collectiveState)
                         .stream()
                         .min(Comparator.comparing(o -> gradientMap.get(o)))
                         .orElse(null);
    }

    public Subspace<CS> getSubspace() {
        return subspace;
    }

    public static <SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>>
    GradientSubspacePlan<SS, CS, D> from(Subspace<CS> subspace, Map<CS, D> distancesMap) {
        return new GradientSubspacePlan<>(subspace, distancesMap);
    }
}
