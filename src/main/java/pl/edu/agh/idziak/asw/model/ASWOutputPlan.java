package pl.edu.agh.idziak.asw.model;


import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;

import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface ASWOutputPlan<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>> {

    CollectivePath<CS> getCollectivePath();

    Set<SubspacePlan<CS>> getSubspacePlans();
}
