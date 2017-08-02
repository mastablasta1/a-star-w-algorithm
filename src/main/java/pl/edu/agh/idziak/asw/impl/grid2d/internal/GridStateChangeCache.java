package pl.edu.agh.idziak.asw.impl.grid2d.internal;

public class GridStateChangeCache {
    private final int rows;
    private final int cols;
    private boolean[][][][] stateChangeCache;

    public GridStateChangeCache(int rows, int cols) {
        stateChangeCache = new boolean[rows][][][];
        this.cols = cols;
        this.rows = rows;
    }

    public boolean checkIfValidStateChangeAndStore(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        if (hasStateChangeOccurred(toRow, toCol, fromRow, fromCol)) {
            return false;
        }
        cacheStateChange(fromRow, fromCol, toRow, toCol);
        return true;
    }

    private boolean hasStateChangeOccurred(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        boolean[][][] level1 = stateChangeCache[fromRow];
        if (level1 == null) return false;
        boolean[][] level2 = stateChangeCache[fromRow][fromCol];
        if (level2 == null) return false;
        boolean[] level3 = stateChangeCache[fromRow][fromCol][toRow];
        return level3 != null && level3[toCol];
    }

    private void cacheStateChange(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        boolean[][][] level1 = stateChangeCache[fromRow];
        if (level1 == null) level1 = stateChangeCache[fromRow] = new boolean[cols][][];
        boolean[][] level2 = level1[fromCol];
        if (level2 == null) level2 = stateChangeCache[fromRow][fromCol] = new boolean[rows][];
        boolean[] level3 = level2[toRow];
        if (level3 == null) level3 = stateChangeCache[fromRow][fromCol][toRow] = new boolean[cols];
        level3[toCol] = true;
    }
}
