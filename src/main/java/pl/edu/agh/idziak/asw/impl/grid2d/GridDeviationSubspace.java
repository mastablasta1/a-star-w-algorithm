package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.wavefront.DeviationSubspace;

import java.util.Set;

/**
 * Created by Tomasz on 14.03.2017.
 */
public interface GridDeviationSubspace extends DeviationSubspace<GridCollectiveState> {

    Set<GridEntityState> getContainedEntityStates();
}
