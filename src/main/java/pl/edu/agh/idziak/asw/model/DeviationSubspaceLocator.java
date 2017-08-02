package pl.edu.agh.idziak.asw.model;

import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;

import java.util.Set;

/**
 * Created by Tomasz on 21.02.2017.
 */
public interface DeviationSubspaceLocator<IP extends InputPlan<?,CS,?>, CS extends CollectiveState<?>> {
    Set<? extends DeviationSubspace<CS>> findDeviationSubspaces(IP inputPlan, CollectivePath<CS> collectivePath);
}
