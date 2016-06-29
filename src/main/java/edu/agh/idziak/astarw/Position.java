package edu.agh.idziak.astarw;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public interface Position<U extends Comparable<U>> {

    List<U> get();
}
