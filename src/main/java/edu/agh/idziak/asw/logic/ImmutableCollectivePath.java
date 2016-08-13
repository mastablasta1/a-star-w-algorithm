package edu.agh.idziak.asw.logic;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.asw.CollectivePath;
import edu.agh.idziak.asw.CollectiveState;
import edu.agh.idziak.asw.EntityState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ImmutableCollectivePath<P extends Comparable<P>> implements CollectivePath<P> {
    private List<CollectiveState<P>> path;

    private ImmutableCollectivePath(List<? extends CollectiveState<P>> path) {
        this.path = ImmutableList.copyOf(path);
    }

    static <E, P extends Comparable<P>> ImmutableCollectivePath<P> from(List<? extends CollectiveState<P>> path) {
        return new ImmutableCollectivePath<>(path);
    }

    @Override
    public List<CollectiveState<P>> get() {
        return path;
    }

    @Override
    public List<EntityState<P>> getPathFor(Object entity) {
        return null; // FIXME
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        path.forEach(uEntityState -> sb.append(uEntityState).append("->\n"));
        sb.setLength(sb.length() - 3);
        return sb.append("}").toString();
    }
}
