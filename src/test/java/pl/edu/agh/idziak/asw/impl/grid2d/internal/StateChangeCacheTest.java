package pl.edu.agh.idziak.asw.impl.grid2d.internal;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StateChangeCacheTest {
    @Test
    public void checkIfValidStateChangeAndStore() throws Exception {
        StateChangeCache testSubject = new StateChangeCache(7, 7);

        assertTrue(checkAndStore(testSubject, 0, 0, 0, 1));
        assertTrue(checkAndStore(testSubject, 0, 0, 0, 1));
        assertFalse(checkAndStore(testSubject, 0, 1, 0, 0));

        assertTrue(checkAndStore(testSubject, 0, 0, 0, 0));
        assertFalse(checkAndStore(testSubject, 0, 0, 0, 0));

        assertTrue(checkAndStore(testSubject, 1, 1, 2, 2));
        assertTrue(checkAndStore(testSubject, 1, 1, 3, 3));
    }

    private boolean checkAndStore(StateChangeCache testSubject, int fromRow, int fromCol, int toRow, int toCol) {
        return testSubject.checkIfValidStateChangeAndStore((byte) fromRow, (byte) fromCol, (byte) toRow, (byte) toCol);
    }

}