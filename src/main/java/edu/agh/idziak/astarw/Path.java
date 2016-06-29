package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface Path<U extends Comparable<U>> {
    List<EntityState<U>> get();
}
