package pl.edu.agh.idziak.asw.model;



import java.util.List;

/**
 * Created by Tomasz on 29.06.2016.
 */

public interface CollectivePath<CS extends CollectiveState<?, ?>> {
    List<CS> get();
}
