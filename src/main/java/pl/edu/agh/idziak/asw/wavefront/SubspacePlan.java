package pl.edu.agh.idziak.asw.wavefront;


import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveState;

import java.util.Set;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface SubspacePlan<CS extends CollectiveState<?, ?>> {

    CS getNextMove(CS collectiveState);

    CollectivePath<CS> constructPath(CS start, CS goal);

    Subspace<CS> getSubspace();

    Set<?> getEntities();
}
