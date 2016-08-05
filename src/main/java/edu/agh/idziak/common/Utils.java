package edu.agh.idziak.common;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Tomasz on 31.07.2016.
 */
public class Utils {

    public static <T> List<T> fillList(List<T> list, int n, Supplier<T> supplier) {
        for (int i = 0; i < n; i++) {
            list.add(supplier.get());
        }
        return list;
    }
}
