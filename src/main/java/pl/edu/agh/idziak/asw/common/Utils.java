package pl.edu.agh.idziak.asw.common;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Created by Tomasz on 05.03.2017.
 */
public class Utils {

    public static <T, U> void forEachParallel(Iterable<T> iterable1, Iterable<U> iterable2, BiConsumer<T, U> consumer) {
        Iterator<T> it1 = iterable1.iterator();
        Iterator<U> it2 = iterable2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            consumer.accept(it1.next(), it2.next());
        }
    }

    public static <T, U> boolean interruptableForEach(Iterable<T> iterable1, Iterable<U> iterable2, BiFunction<T, U, Boolean> function) {
        Iterator<T> it1 = iterable1.iterator();
        Iterator<U> it2 = iterable2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!function.apply(it1.next(), it2.next())) {
                return false;
            }
        }
        return true;
    }
}
