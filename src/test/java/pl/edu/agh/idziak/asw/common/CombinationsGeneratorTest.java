package pl.edu.agh.idziak.asw.common;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Tomasz on 21.07.2016.
 */
public class CombinationsGeneratorTest {

    @Test
    public void generateCombinations() throws Exception {

        List<List<String>> choiceArray = ImmutableList.of(
                ImmutableList.of("a", "b"),
                ImmutableList.of("x", "y"),
                ImmutableList.of("p", "q"));

        List<List<String>> result = CombinationsGenerator.generateCombinations(choiceArray, input -> true);

        System.out.println(result);
    }

    @Test
    public void generateCombinationsIterator() throws Exception {

        List<List<String>> choiceArray = ImmutableList.of(
                ImmutableList.of("a", "b"),
                ImmutableList.of("x", "y"),
                ImmutableList.of("p", "q"));

        Iterator<List<String>> result = CombinationsGenerator.combinatorialIterator(choiceArray);

        result.forEachRemaining(System.out::println);
    }

    @Test
    public void testIterableCombinator() throws Exception {
        List<List<String>> choiceArray = ImmutableList.of(
                ImmutableList.of("a", "b"),
                ImmutableList.of("x", "y"),
                ImmutableList.of("p", "q"));

        Iterable<String> result = CombinationsGenerator.iterableCombinator(choiceArray);

        Iterator<String> iterator;
        while ((iterator = result.iterator()) != null) {
            iterator.forEachRemaining((s) -> System.out.print(s + ","));
            System.out.println();
        }
    }

    @Test
    public void generateCombinationsIteratorSimple() throws Exception {

        List<List<String>> choiceArray = ImmutableList.of(
                ImmutableList.of("a", "b"),
                ImmutableList.of("x", "y"));

        Iterator<List<String>> result = CombinationsGenerator.combinatorialIterator(choiceArray);

        result.forEachRemaining(System.out::println);
    }


}