package pl.edu.agh.idziak.asw.impl.grid2d.internal;

import java.util.BitSet;

public class EntityStateUniquenessCache {
    private final int rows;
    private final int cols;
    // private boolean[][] cache;
    private BitSet cache;

    public EntityStateUniquenessCache(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cache = new BitSet(rows * cols);
        reset();
    }

    public boolean checkIfUniqueAndStore(byte row, byte col) {
        int bitIndex = row * cols + col;
        if (cache.get(bitIndex)) {
            return false;
        }
        cache.set(bitIndex);
        return true;
    }

    public void reset() {
        cache.clear();
    }
}
