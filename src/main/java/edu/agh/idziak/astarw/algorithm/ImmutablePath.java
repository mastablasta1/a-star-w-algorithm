package edu.agh.idziak.astarw.algorithm;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.GlobalPath;
import edu.agh.idziak.astarw.GlobalState;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ImmutablePath<U extends Comparable<U>> implements GlobalPath<U> {
    private List<GlobalState<U>> path;

    private ImmutablePath(List<? extends GlobalState<U>> path) {
        this.path = ImmutableList.copyOf(path);
    }

    static <T extends Comparable<T>> ImmutablePath<T> from(List<? extends GlobalState<T>> path) {
        return new ImmutablePath<T>(path);
    }

    @Override
    public List<GlobalState<U>> get() {
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        path.forEach(uEntityState -> sb.append(uEntityState).append("->"));
        sb.setLength(sb.length() - 2);
        return sb.append("}").toString();
    }
}
