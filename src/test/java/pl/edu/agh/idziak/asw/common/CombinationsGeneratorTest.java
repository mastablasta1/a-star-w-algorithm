package pl.edu.agh.idziak.asw.common;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

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

}