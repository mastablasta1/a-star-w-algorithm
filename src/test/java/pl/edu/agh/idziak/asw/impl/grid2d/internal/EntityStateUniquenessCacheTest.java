package pl.edu.agh.idziak.asw.impl.grid2d.internal;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntityStateUniquenessCacheTest {
    @Test
    public void checkIfUniqueAndStore() throws Exception {
        EntityStateUniquenessCache testSubject = new EntityStateUniquenessCache(5, 5);

        assertTrue(testSubject.checkIfUniqueAndStore((byte) 1, (byte) 1));
        assertFalse(testSubject.checkIfUniqueAndStore((byte) 1, (byte) 1));

        assertTrue(testSubject.checkIfUniqueAndStore((byte) 2, (byte) 1));
        assertFalse(testSubject.checkIfUniqueAndStore((byte) 2, (byte) 1));

        assertTrue(testSubject.checkIfUniqueAndStore((byte) 1, (byte) 2));
        assertFalse(testSubject.checkIfUniqueAndStore((byte) 1, (byte) 2));

        testSubject.reset();

        assertTrue(testSubject.checkIfUniqueAndStore((byte) 1, (byte) 1));
    }

}