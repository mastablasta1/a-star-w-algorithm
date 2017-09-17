package pl.edu.agh.idziak.asw.model;


import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface ASWOutputPlan<CS extends CollectiveState> {

    CollectivePath<CS> getCollectivePath();

    Set<DeviationSubspacePlan<CS>> getDeviationSubspacePlans();
}
