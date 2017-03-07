package pl.edu.agh.idziak.asw.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

/**
 * Created by Tomasz on 21.07.2016.
 */
public class CombinationsGenerator {

    public static <T> List<List<T>> generateCombinations(List<List<T>> choiceArray, Predicate<List<T>> predicate) {
        Preconditions.checkNotNull(choiceArray);

        List<Iterator<T>> iterators = choiceArray.stream()
                                                 .map(Collection::iterator)
                                                 .collect(Collectors.toList());
        List<List<T>> outputList = new LinkedList<>();

        int position = 0;
        List<T> currentCombination = getInitialCombination(iterators);

        if (predicate == null || predicate.test(currentCombination))
            outputList.add(ImmutableList.copyOf(currentCombination));

        while (true) {
            Iterator<T> posIterator = iterators.get(position);
            if (posIterator.hasNext()) {
                T next = posIterator.next();
                currentCombination.set(position, next);
                if (predicate == null || predicate.test(currentCombination))
                    outputList.add(ImmutableList.copyOf(currentCombination));
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

        return outputList;
    }

    private static <T> List<T> getInitialCombination(List<Iterator<T>> iterators) {
        return iterators.stream()
                        .map(Iterator::next)
                        .collect(toCollection(ArrayList::new));
    }

    public static <T> Iterator<List<T>> combinatorialIterator(List<List<T>> lists) {
        return new Iterator<List<T>>() {
            private final List<Iterator<T>> iterators = lists.stream()
                                                             .map(List::iterator)
                                                             .collect(toCollection(() ->
                                                                     new ArrayList<>(lists.size())));
            int position = 0;
            private List<T> currentCombination = iterators.stream()
                                                          .map(Iterator::next)
                                                          .collect(toCollection(() ->
                                                                  new ArrayList<>(iterators.size())));

            @Override public boolean hasNext() {
                return position <= iterators.size();
            }

            @Override public List<T> next() {
                List<T> currentCombinationCopy = new ArrayList<>(currentCombination);

                int size = iterators.size();
                if (position++ >= size) {
                    return currentCombinationCopy;
                }
                Iterator<T> iteratorAtPosition = iterators.get(position);
                if (iteratorAtPosition.hasNext()) {
                    T next = iteratorAtPosition.next();
                    currentCombination.set(position, next);
                    position = 0;
                } else {
                    if (position >= size - 1) {
                        Iterator<T> newIterator = lists.get(position).iterator();
                        iterators.set(position, newIterator);
                        currentCombination.set(position, newIterator.next());
                    }
                    position++;
                }

                return currentCombinationCopy;
            }
        };
    }

}
