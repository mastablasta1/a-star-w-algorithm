package pl.edu.agh.idziak.asw.impl.gridarray;

/**
 * Created by Tomasz on 18.03.2017.
 */
public class OptimizedArrayList<T> {
    private Object[] elements;
    private int size = 0;
    private final int capacity;

    public OptimizedArrayList(int capacity) {
        this.elements = new Object[capacity];
        this.capacity = capacity;
    }

    public OptimizedArrayList() {
        this(0);
    }

    public void add(T element) {
        elements[size++] = element;
    }

    public void clear() {
        size = 0;
    }

    public void resizeAndClear(int capacity) {
        elements = new Object[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (i >= size) {
            throw new ArrayIndexOutOfBoundsException("size=" + size + ", index=" + i);
        }
        return (T) elements[i];
    }

    public int getCapacity() {
        return capacity;
    }
}
