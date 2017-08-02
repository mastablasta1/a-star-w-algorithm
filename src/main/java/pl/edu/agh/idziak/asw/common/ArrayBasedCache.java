package pl.edu.agh.idziak.asw.common;

import java.util.function.Function;

/**
 * Created by Tomasz on 23.04.2017.
 */
public class ArrayBasedCache<T> {

    private final int size;
    private Node root;
    private Function<byte[], T> objectGenerator;

    public ArrayBasedCache(int dimensionSize, Function<byte[], T> objectGenerator) {
        this.size = dimensionSize;
        this.objectGenerator = objectGenerator;
        root = new Node(size);
    }

    @SuppressWarnings("unchecked")
    public T get(byte[] stateArray) {
        Node current = root;

        int i;
        int reqPos;
        Node next;
        for (i = 0; i < stateArray.length - 1; i++) {
            reqPos = stateArray[i];
            if ((next = current.positions[reqPos]) == null) {
                next = new Node(size);
                current.positions[reqPos] = next;
            }
            current = next;
        }

        reqPos = stateArray[i];
        next = current.positions[reqPos];

        if (next instanceof Leaf) {
            return ((Leaf<T>) next).collectiveState;
        } else if (next == null) {
            T newValue = objectGenerator.apply(stateArray);
            current.positions[reqPos] = new Leaf<>(size, newValue);
            return newValue;
        }
        throw new AssertionError();
    }

    private static class Node {
        private Node[] positions;

        private Node(int size) {
            positions = new Node[size];
        }
    }

    private static class Leaf<T> extends Node {
        private T collectiveState;

        public Leaf(int size, T collectiveState) {
            super(size);
            this.collectiveState = collectiveState;
        }
    }
}
