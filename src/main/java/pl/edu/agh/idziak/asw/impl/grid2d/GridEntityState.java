package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.base.Objects;

/**
 * Created by Tomasz on 02/05/2017.
 */
public class GridEntityState {
    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public GridEntityState(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static GridEntityState of(int row, int col) {
        return new GridEntityState(row, col);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridEntityState that = (GridEntityState) o;

        return Objects.equal(this.row, that.row) &&
                Objects.equal(this.col, that.col);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(row, col);
    }
}
