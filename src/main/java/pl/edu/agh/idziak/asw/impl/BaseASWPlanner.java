package pl.edu.agh.idziak.asw.impl;

import pl.edu.agh.idziak.asw.astar.AStarStateMonitor;
import pl.edu.agh.idziak.asw.astar.CollectiveAStarImpl;
import pl.edu.agh.idziak.asw.astar.SortingPreference;
import pl.edu.agh.idziak.asw.impl.grid2d.GridCollectiveDeviationSubspace;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;
import pl.edu.agh.idziak.asw.wavefront.impl.WavefrontImpl;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by Tomasz on 21.02.2017.
 */
public abstract class BaseASWPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends CollectiveStateSpace<CS>, CS extends CollectiveState, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final CollectiveAStarImpl<SS, CS, D> collectiveAStar;
    private final Wavefront<CS, D> wavefront;
    private DeviationSubspaceLocator<IP, CS> deviationSubspaceLocator;

    public BaseASWPlanner(AbstractNumberHandler<D> numberHandler, DeviationSubspaceLocator<IP, CS> deviationSubspaceLocator) {
        this.collectiveAStar = new CollectiveAStarImpl<>(numberHandler, SortingPreference.NONE);
        this.wavefront = new WavefrontImpl<>(numberHandler);
        this.deviationSubspaceLocator = deviationSubspaceLocator;
    }

    @Override
    public ASWOutputPlan<CS> calculatePlan(IP inputPlan) {
        CollectivePath<CS> collectivePath = collectiveAStar.calculatePath(inputPlan);
        if (collectivePath == null) {
            return ImmutableASWOutputPlan.from(null, null);
        }

        Collection<? extends DeviationSubspace<CS>> subspaces = deviationSubspaceLocator.findDeviationSubspaces(inputPlan, collectivePath);
        if (subspaces == null) {
            subspaces = Collections.emptySet();
        }

        Set<DeviationSubspacePlan<CS>> deviationSubspacePlans = subspaces.stream()
                .sorted(Comparator.comparing(o -> o.getTargetState().size()))
                .map(devSubspace -> wavefront.buildPlanForSubspace(devSubspace, inputPlan.getDistanceHeuristic()))
                .collect(toCollection(LinkedHashSet::new));

        return ImmutableASWOutputPlan.from(collectivePath, deviationSubspacePlans);
    }

    public void setAStarCurrentStateMonitor(AStarStateMonitor<CS> monitor) {
        collectiveAStar.setaStarStateMonitor(monitor);
    }

    public void setAStarSortingPreference(SortingPreference sortingPreference) {
        collectiveAStar.setSortingPreference(sortingPreference);
    }

    public int countStatesInSubspaces(Collection<? extends DeviationSubspace<CS>> collection) {
        return collection.stream()
                .map(o -> (GridCollectiveDeviationSubspace) o)
                .mapToInt(GridCollectiveDeviationSubspace::countCollectiveStates)
                .sum();
    }

    public DeviationSubspaceLocator<IP, CS> getDeviationSubspaceLocator() {
        return deviationSubspaceLocator;
    }

    public void setDeviationSubspaceLocator(DeviationSubspaceLocator<IP, CS> deviationSubspaceLocator) {
        this.deviationSubspaceLocator = checkNotNull(deviationSubspaceLocator);
    }
}
