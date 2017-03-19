package pl.edu.agh.idziak.asw.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class ImmutableCollectivePath<CS extends CollectiveState<?>> implements CollectivePath<CS> {
    private List<CS> path;

    private ImmutableCollectivePath(List<CS> path) {
        this.path = ImmutableList.copyOf(path);
    }

    public static <CS extends CollectiveState<?>> ImmutableCollectivePath<CS> from(List<CS> path) {
        return new ImmutableCollectivePath<>(path);
    }

    @Override
    public List<CS> get() {
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        path.forEach(uEntityState -> sb.append(uEntityState)
                                       .append("\n"));
        sb.setLength(sb.length() - 2);
        return sb.append("}")
                 .toString();
    }
}
