package pl.edu.agh.idziak.asw.wavefront;


import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveState;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface DeviationSubspacePlan<CS extends CollectiveState> {

    CS getNextMove(CS collectiveState);

    CollectivePath<CS> getPathToGoalFrom(CS start);

    DeviationSubspace<CS> getDeviationSubspace();
}
