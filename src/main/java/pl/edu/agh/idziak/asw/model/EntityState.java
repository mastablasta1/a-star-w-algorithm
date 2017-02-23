package pl.edu.agh.idziak.asw.model;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface EntityState<P extends Comparable<P>> {
    List<P> get();
}
