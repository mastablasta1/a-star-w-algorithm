package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface GlobalPath<U extends Comparable<U>> {
    List<GlobalState<U>> get();
}
