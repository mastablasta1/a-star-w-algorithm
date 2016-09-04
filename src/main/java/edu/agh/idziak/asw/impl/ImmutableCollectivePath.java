package edu.agh.idziak.asw.impl;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.asw.CollectivePath;
import edu.agh.idziak.asw.CollectiveState;
import edu.agh.idziak.asw.EntityState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ImmutableCollectivePath<CS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>>
        implements CollectivePath<CS, ES, P> {
    private List<CS> path;

    private ImmutableCollectivePath(List<CS> path) {
        this.path = ImmutableList.copyOf(path);
    }

    static <CS extends CollectiveState<ES, P>, ES extends EntityState<P>, P extends Comparable<P>>
    ImmutableCollectivePath<CS, ES, P> from(List<CS> path) {
        return new ImmutableCollectivePath<>(path);
    }

    @Override
    public List<CS> get() {
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        path.forEach(uEntityState -> sb.append(uEntityState).append("\n"));
        sb.setLength(sb.length() - 3);
        return sb.append("}").toString();
    }
}
