package edu.agh.idziak.common;

import edu.agh.idziak.astarw.AbstractNumberHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class IntegerHandler implements AbstractNumberHandler<Integer> {
    private static final Integer ZERO = 0;
    private static final IntegerHandler instance = new IntegerHandler();

    private IntegerHandler() {
    }

    public static IntegerHandler getInstance() {
        return instance;
    }

    @Override
    public Integer add(Integer one, Integer two) {
        return one + two;
    }

    @Override
    public boolean lessThan(Integer one, Integer two) {
        return one < two;
    }

    @Override
    public boolean greaterOrEqual(Integer one, Integer two) {
        return one > two;
    }

    @Override
    public Integer getZero() {
        return ZERO;
    }
}
