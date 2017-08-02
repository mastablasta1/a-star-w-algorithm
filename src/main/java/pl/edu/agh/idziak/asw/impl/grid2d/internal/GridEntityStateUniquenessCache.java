package pl.edu.agh.idziak.asw.impl.grid2d.internal;

public class GridEntityStateUniquenessCache {
    private boolean[][] cache;

    public GridEntityStateUniquenessCache(int rows, int cols) {
        cache = new boolean[rows][cols];
    }

    public boolean checkIfUniqueAndStore(byte row, byte col){
        if (cache[row][col]) {
            return false;
        }
        cache[row][col] = true;
        return true;
    }
}
