package edu.agh.idziak.astarw.algorithm;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.GlobalPath;
import edu.agh.idziak.astarw.GlobalState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ImmutablePath<P extends Comparable<P>> implements GlobalPath<P> {
    private List<GlobalState<P>> path;

    private ImmutablePath(List<? extends GlobalState<P>> path) {
        this.path = ImmutableList.copyOf(path);
    }

    static <T extends Comparable<T>> ImmutablePath<T> from(List<? extends GlobalState<T>> path) {
        return new ImmutablePath<T>(path);
    }

    @Override
    public List<GlobalState<P>> get() {
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        path.forEach(uEntityState -> sb.append(uEntityState).append("->\n"));
        sb.setLength(sb.length() - 3);
        return sb.append("}").toString();
    }
}
