package pl.edu.agh.idziak.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 21.07.2016.
 */
public class CombinationsGenerator {

    public static <T> List<List<T>> generateCombinations(List<List<T>> choiceArray, Predicate<List<T>> predicate) {
        Preconditions.checkNotNull(choiceArray);

        List<Iterator<T>> iterators = choiceArray.stream()
                .map(Collection::iterator)
                .collect(Collectors.toList());
        List<List<T>> resultList = new LinkedList<>();

        int position = 0;
        List<T> currentCombination = getInitialCombination(iterators);
        if (predicate.test(currentCombination))
            resultList.add(ImmutableList.copyOf(currentCombination));

        while (true) {
            Iterator<T> posIterator = iterators.get(position);
            if (posIterator.hasNext()) {
                T next = posIterator.next();
                currentCombination.set(position, next);
                if (predicate.test(currentCombination))
                    resultList.add(ImmutableList.copyOf(currentCombination));
                position = 0;
            } else {
                Iterator<T> newIterator = choiceArray.get(position).iterator();
                iterators.set(position, newIterator);
                currentCombination.set(position, newIterator.next());
                if (++position >= iterators.size()) {
                    break;
                }
            }
        }

        return resultList;
    }

    private static <T> List<T> getInitialCombination(List<Iterator<T>> iterators) {
        ArrayList<T> initialCombination = new ArrayList<>();
        iterators.forEach(it -> initialCombination.add(it.next()));
        return initialCombination;
    }


}
