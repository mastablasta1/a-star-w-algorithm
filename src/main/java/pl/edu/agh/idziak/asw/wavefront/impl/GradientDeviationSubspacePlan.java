package pl.edu.agh.idziak.asw.wavefront.impl;

import com.google.common.collect.ImmutableMap;
import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.ImmutableCollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveStateSpace;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomasz on 20.02.2017.
 */
public class GradientDeviationSubspacePlan<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>>
        implements DeviationSubspacePlan<CS> {

    private final DeviationSubspace<CS> deviationSubspace;
    private final Map<CS, D> gradientMap;

    public GradientDeviationSubspacePlan(DeviationSubspace<CS> deviationSubspace, Map<CS, D> distancesMap) {
        this.deviationSubspace = deviationSubspace;
        this.gradientMap = ImmutableMap.copyOf(distancesMap);
    }

    @Override
    public CS getNextMove(CS collectiveState) {
        return deviationSubspace.getNeighborStatesOf(collectiveState)
                         .stream()
                         .min(Comparator.comparing(gradientMap::get))
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

    public DeviationSubspace<CS> getDeviationSubspace() {
        return deviationSubspace;
    }

}
