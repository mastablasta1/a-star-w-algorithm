package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.wavefront.Subspace;

import java.util.Set;

/**
 * Created by Tomasz on 14.03.2017.
 */
public interface G2DSubspace extends Subspace<G2DCollectiveState> {

    @Override boolean contains(G2DCollectiveState collectiveState);

    @Override G2DCollectiveState getTargetState();

    Set<G2DEntityState> getContainedEntityStates();
}
