package pl.edu.agh.idziak.asw.impl;

import pl.edu.agh.idziak.asw.astar.CollectiveAStar;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;
import pl.edu.agh.idziak.asw.wavefront.impl.WavefrontImpl;

import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class BaseASWPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final CollectiveAStar<SS, CS, D> collectiveAStar;
    private final Wavefront<SS, CS, D> wavefront;
    private final DeviationZonesFinder<IP, CS> deviationZonesFinder;

    public BaseASWPlanner(AbstractNumberHandler<D> numberHandler, DeviationZonesFinder<IP, CS> deviationZonesFinder) {
        collectiveAStar = new CollectiveAStar<>(numberHandler);
        wavefront = new WavefrontImpl<>(numberHandler);
        this.deviationZonesFinder = deviationZonesFinder;
    }

    @Override public ASWOutputPlan<SS, CS> calculatePlan(IP inputPlan) {

        CollectivePath<CS> collectivePath = collectiveAStar.calculatePath(inputPlan);

        Set<Subspace<CS>> subspaces = deviationZonesFinder.findDeviationZones(inputPlan, collectivePath);

        if (subspaces == null) {
            subspaces = Collections.emptySet();
        }

        Set<SubspacePlan<SS, CS>> devZonePlans =
                subspaces.stream()
                         .map(devZone -> wavefront.buildPlanForSubspace(
                                      devZone,
                                      inputPlan.getStateSpace(),
                                      inputPlan.getCostFunction()))
                         .collect(toSet());

        return ImmutableASWOutputPlan.from(collectivePath, devZonePlans);
    }

}
