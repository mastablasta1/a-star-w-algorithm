package edu.agh.idziak.astarw.algorithm;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.DeviationZone;
import edu.agh.idziak.astarw.EntityOutputPlan;
import edu.agh.idziak.astarw.EntityState;

import java.util.List;

/**
 * Created by Tomasz on 31.07.2016.
 */
public abstract class BasicEntityOutputPlan<P extends Comparable<P>> implements EntityOutputPlan<P> {

    private List<DeviationZone<P>> deviationZones;
    private List<EntityState<P>> path;

    public BasicEntityOutputPlan(List<DeviationZone<P>> deviationZones, List<EntityState<P>> path) {
        this.deviationZones = ImmutableList.copyOf(deviationZones);
        this.path = ImmutableList.copyOf(path);
    }

    @Override
    public List<DeviationZone<P>> getDeviationZones() {
        return deviationZones;
    }

    @Override
    public List<EntityState<P>> getPath() {
        return path;
    }
}
