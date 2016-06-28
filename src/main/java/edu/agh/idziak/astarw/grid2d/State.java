package edu.agh.idziak.astarw.grid2d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class State {
    private List<Point> points;

    public State(List<Point> points) {
        this.points = new ArrayList<>(points);
    }

    public List<Point> getPoints() {
        return points;
    }

    public static State of(List<Point> points) {
        return new State(points);
    }
}
