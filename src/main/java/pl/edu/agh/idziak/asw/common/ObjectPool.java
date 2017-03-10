package pl.edu.agh.idziak.asw.common;

import java.util.ArrayDeque;
import java.util.function.Supplier;

/**
 * Created by Tomasz on 09.03.2017.
 */
public class ObjectPool<T> {

    private ArrayDeque<T> deque = new ArrayDeque<>();
    private Supplier<T> producer;

    public ObjectPool(Supplier<T> producer) {
        this.producer = producer;
    }

    public T borrow() {
        T obj = deque.pollFirst();
        return obj == null ? producer.get() : obj;
    }

    public void release(T object) {
        deque.add(object);
    }
}
