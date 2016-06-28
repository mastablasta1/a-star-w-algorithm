package edu.agh.idziak.astarw;

import edu.agh.idziak.common.Range;

import java.util.List;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface DeviationZone<T extends Comparable<T>> {
    List<Range<T>> getBounds();
}
