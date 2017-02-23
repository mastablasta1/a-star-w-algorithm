package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.model.ImmutableMapCollectiveState;

import java.util.Map;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class G2DCollectiveState extends ImmutableMapCollectiveState<G2DEntityState, Integer> {

    public G2DCollectiveState(Map<?, G2DEntityState> entityStates) {
        super(entityStates);
    }

    public static G2DCollectiveState from(Map<?, G2DEntityState> entityStateMap) {
        return new G2DCollectiveState(entityStateMap);
    }
}
