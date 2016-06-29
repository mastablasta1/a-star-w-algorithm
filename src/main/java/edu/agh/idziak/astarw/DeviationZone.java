package edu.agh.idziak.astarw;

import edu.agh.idziak.common.Range;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface DeviationZone<D extends Comparable<D>> {
    Range<EntityState<D>> getBounds();
}
