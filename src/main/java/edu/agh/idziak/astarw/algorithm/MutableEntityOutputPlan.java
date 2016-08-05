package edu.agh.idziak.astarw.algorithm;

import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.EntityState;

import java.util.List;

/**
 * Created by Tomasz on 31.07.2016.
 */
class MutableEntityOutputPlan<P extends Comparable<P>> {

    private List<DeviationZone<P>> deviationZones;
    private List<EntityState<P>> path;

    public List<DeviationZone<P>> getDeviationZones() {
        return deviationZones;
    }

    public List<EntityState<P>> getPath() {
        return path;
    }

    public void setDeviationZones(List<DeviationZone<P>> deviationZones) {
        this.deviationZones = deviationZones;
    }

    public void setPath(List<EntityState<P>> path) {
        this.path = path;
    }
}
