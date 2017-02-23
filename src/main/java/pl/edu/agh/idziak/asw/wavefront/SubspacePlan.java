package pl.edu.agh.idziak.asw.wavefront;


import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.StateSpace;

/**
 * Created by Tomasz on 13.08.2016.
 */
public interface SubspacePlan<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>> {

    CS getNextMove(CS collectiveState, SS stateSpace);
    Subspace<CS> getSubspace();
}
