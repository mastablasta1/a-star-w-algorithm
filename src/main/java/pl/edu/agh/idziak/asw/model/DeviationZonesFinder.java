package pl.edu.agh.idziak.asw.model;

import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.Set;

/**
 * Created by Tomasz on 21.02.2017.
 */
public interface DeviationZonesFinder<IP extends InputPlan<?,CS,?>, CS extends CollectiveState<?>> {
    Set<? extends Subspace<CS>> findDeviationZones(IP inputPlan, CollectivePath<CS> collectivePath);
}
