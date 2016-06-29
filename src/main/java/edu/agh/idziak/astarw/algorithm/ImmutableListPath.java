package edu.agh.idziak.astarw.algorithm;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.astarw.EntityState;
import edu.agh.idziak.astarw.Path;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
class ImmutableListPath<U extends Comparable<U>> implements Path<U> {
    private List<EntityState<U>> path;

    private ImmutableListPath(List<EntityState<U>> path) {
        this.path = ImmutableList.copyOf(path);
    }

    static <T extends Comparable<T>> ImmutableListPath<T> from(List<EntityState<T>> path) {
        return new ImmutableListPath<>(path);
    }

    @Override
    public List<EntityState<U>> get() {
        return path;
    }
}
