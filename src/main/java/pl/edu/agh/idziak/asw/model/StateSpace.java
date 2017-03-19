package pl.edu.agh.idziak.asw.model;


import java.util.Collection;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface StateSpace<CS extends CollectiveState<?>> {

    Collection<CS> getNeighborStatesOf(CS globalState);
}
