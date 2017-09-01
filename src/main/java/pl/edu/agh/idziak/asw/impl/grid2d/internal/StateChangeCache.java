package pl.edu.agh.idziak.asw.impl.grid2d.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.idziak.asw.impl.grid2d.NeighborhoodType;

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

    public boolean checkIfValidStateChangeAndStore(NeighborhoodType neighborhood, byte fromRow, byte fromCol, byte toRow, byte toCol) {
        if (neighborhood == NeighborhoodType.MOORE
                && hasStateChangeOccurredMoore(fromRow, fromCol, toRow, toCol)) {
            return false;
        } else if (hasStateChangeOccurred(toRow, toCol, fromRow, fromCol)) {
            return false;
        }
        cacheStateChange(fromRow, fromCol, toRow, toCol);
        return true;
    }

    private boolean hasStateChangeOccurredMoore(byte fromRow, byte fromCol, byte toRow, byte toCol) {
        if (toRow != fromRow) {
            if (toCol != fromCol) {
                // diagonal movement
                if (hasStateChangeOccurred(fromRow, toCol, toRow, fromCol)
                        || hasStateChangeOccurred(toRow, fromCol, fromRow, toCol)) {
                    return true;
                }
            } else if (hasStateChangeOccurred(toRow, fromCol, fromRow, toCol)) {
                // vertical
                return true;
            }
        } else if (toCol != fromCol && hasStateChangeOccurred(fromRow, toCol, toRow, fromCol)) {
            // horizontal
            return true;
        }
        return false;
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

    public void reset() {
        cache.clear();
    }

}
