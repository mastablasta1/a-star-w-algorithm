package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface GlobalPath<P extends Comparable<P>> {
    List<GlobalState<P>> get();
}
