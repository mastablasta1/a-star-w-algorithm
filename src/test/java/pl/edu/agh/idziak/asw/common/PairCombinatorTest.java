package pl.edu.agh.idziak.asw.common;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tomasz on 10.09.2016.
 */
public class PairCombinatorTest {
    @Test
    public void testMany() throws Exception {
        PairCombinator<Integer> testSubject = new PairCombinator<>(ImmutableList.of(1, 2, 3));

        List<SingleTypePair<Integer>> expectedPairs = ImmutableList.of(
                Pair.ofSingleType(1, 2),
                Pair.ofSingleType(1, 3),
                Pair.ofSingleType(2, 3)
        );

        iteratePairCombinator(testSubject, expectedPairs);
    }

    @Test
    public void testSinglePair() throws Exception {
        PairCombinator<Integer> testSubject = new PairCombinator<>(ImmutableList.of(1, 2));

        List<SingleTypePair<Integer>> expectedPairs = ImmutableList.of(
                Pair.ofSingleType(1, 2)
        );

        iteratePairCombinator(testSubject, expectedPairs);
    }

    @Test
    public void testOneElement() throws Exception {
        PairCombinator<Integer> testSubject = new PairCombinator<>(ImmutableList.of(1));
        List<SingleTypePair<Integer>> expectedPairs = ImmutableList.of();
        iteratePairCombinator(testSubject, expectedPairs);
    }

    @Test
    public void testEmpty() throws Exception {
        PairCombinator<Integer> testSubject = new PairCombinator<>(ImmutableList.of());
        List<SingleTypePair<Integer>> expectedPairs = ImmutableList.of();
        iteratePairCombinator(testSubject, expectedPairs);
    }

    private static void iteratePairCombinator(PairCombinator<Integer> testSubject, List<SingleTypePair<Integer>> expectedPairs) {
        int i = 0;
        while (testSubject.hasNext()) {
            testSubject.next();
            assertTrue(i < expectedPairs.size());
            SingleTypePair<Integer> pair = expectedPairs.get(i++);
            assertEquals(pair.getOne(), testSubject.getCurrentFirst());
            assertEquals(pair.getTwo(), testSubject.getCurrentSecond());
        }
        assertEquals(expectedPairs.size(), i);
    }
}