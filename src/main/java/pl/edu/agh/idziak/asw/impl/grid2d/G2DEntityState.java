package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.model.ImmutableEntityState;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class G2DEntityState extends ImmutableEntityState<Integer> {

    public G2DEntityState(Integer row, Integer col) {
        super(row, col);
    }

    public Integer getRow() {
        return get().get(0);
    }

    public Integer getCol() {
        return get().get(1);
    }

    public static G2DEntityState of(int row, int col) {
        return new G2DEntityState(row, col);
    }
}
