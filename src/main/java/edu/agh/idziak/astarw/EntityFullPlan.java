package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface EntityFullPlan<E, P> extends EntityBasicPlan<E, P> {
    List<P> getPathWithoutEndpoints();

    List<P> getFullPath();
}
