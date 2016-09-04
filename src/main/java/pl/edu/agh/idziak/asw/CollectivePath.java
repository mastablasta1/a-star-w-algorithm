package pl.edu.agh.idziak.asw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */

public interface CollectivePath<CS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>> {
    List<CS> get();

}
