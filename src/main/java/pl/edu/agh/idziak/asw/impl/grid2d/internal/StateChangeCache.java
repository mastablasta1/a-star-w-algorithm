package pl.edu.agh.idziak.asw.impl.grid2d.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;

public class StateChangeCache {
    private static final Logger LOG = LoggerFactory.getLogger(StateChangeCache.class);
    private final int rows;
    private final int cols;
    private BitSet cache;

    public StateChangeCache(int rows, int cols) {
        int bitsNum = rows * rows * cols * cols;
        cache = new BitSet(bitsNum);
        this.cols = cols;
        this.rows = rows;
        LOG.debug("Initialized state change cache with {} bits", bitsNum);
    }

    public boolean checkIfValidStateChangeAndStore(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        if (hasStateChangeOccurred(toRow, toCol, fromRow, fromCol)) {
            return false;
        }
        cacheStateChange(fromRow, fromCol, toRow, toCol);
        return true;
    }

    private boolean hasStateChangeOccurred(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        return cache.get(fromRow * cols * rows * cols
                + fromCol * rows * cols
                + toRow * cols
                + toCol);
    }

    private void cacheStateChange(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        cache.set(fromRow * cols * rows * cols
                + fromCol * rows * cols
                + toRow * cols
                + toCol);
    }
}
