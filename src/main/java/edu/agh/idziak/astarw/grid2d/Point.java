package edu.agh.idziak.astarw.grid2d;

/**
 * Created by Tomasz on 28.06.2016.
 */
public class Point {
    private final int x;
    private final int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
