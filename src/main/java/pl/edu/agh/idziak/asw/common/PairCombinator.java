package pl.edu.agh.idziak.asw.common;

import java.util.List;

/**
 * Created by Tomasz on 31.07.2016.
 */
public class PairCombinator<T> {

    private List<T> list;
    private int i;
    private int j;
    private int size;
    private T currentFirst;
    private T currentSecond;
    private int firstIndex;
    private int secondIndex;

    public PairCombinator(List<T> list) {
        this.list = list;
        size = list.size();
        i = -1;
        j = size;
    }

    public boolean hasNext() {
        return i < size - 2;
    }

    public void next() {
        if (++j >= size) {
            j = ++i + 1;
        }
        currentFirst = list.get(i);
        currentSecond = list.get(j);
        firstIndex = i;
        secondIndex = j;
    }

    public T getCurrentFirst() {
        return currentFirst;
    }

    public T getCurrentSecond() {
        return currentSecond;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getSecondIndex() {
        return secondIndex;
    }
}
