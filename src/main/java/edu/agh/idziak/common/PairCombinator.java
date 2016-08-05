package edu.agh.idziak.common;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Tomasz on 31.07.2016.
 */
public class PairCombinator<T> implements Iterator<PairCombinator.Entry<T>> {

    private List<T> list;
    private int i;
    private int j;
    private int size;

    public PairCombinator(List<T> list) {
        this.list = list;
        size = list.size();
    }

    @Override
    public boolean hasNext() {
        return i >= size - 1;
    }

    @Override
    public Entry<T> next() {
        if (++j >= size) {
            j = ++i + 1;
        }
        Entry<T> entry = new Entry<>();
        entry.firstElement = list.get(i);
        entry.secondElement = list.get(j);
        entry.firstIndex = i;
        entry.secondIndex = j;
        return entry;
    }

    public static class Entry<T> {
        private int firstIndex;
        private int secondIndex;
        private T firstElement;
        private T secondElement;

        public int getFirstIndex() {
            return firstIndex;
        }

        public int getSecondIndex() {
            return secondIndex;
        }

        public T getFirstElement() {
            return firstElement;
        }

        public T getSecondElement() {
            return secondElement;
        }
    }
}
