package edu.agh.idziak.common;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class ListableObjectPool<T extends Iterable<E>, E> {

    private Node<E> root = new Node<>();

    public void put(T object) {
        Iterator<E> it = object.iterator();

        while (it.hasNext()) {

        }
    }

    private static class Node<T> {
        private Map<T, Node<T>> map;
    }
}
