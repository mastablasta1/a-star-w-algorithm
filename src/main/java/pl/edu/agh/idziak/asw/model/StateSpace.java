package pl.edu.agh.idziak.asw.model;


import java.util.Set;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<CS extends CollectiveState<?, ?>> {

    Set<CS> getNeighborStatesOf(CS globalState);
}
