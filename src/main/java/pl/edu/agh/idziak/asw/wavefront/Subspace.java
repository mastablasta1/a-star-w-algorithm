package pl.edu.agh.idziak.asw.wavefront;


import pl.edu.agh.idziak.asw.model.CollectiveState;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface Subspace<CS extends CollectiveState<?>> {
    boolean contains(CS collectiveState);
    CS getTargetState();
}
