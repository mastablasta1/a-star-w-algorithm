package edu.agh.idziak.common;

import edu.agh.idziak.astarw.AbstractNumberHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class DoubleHandler implements AbstractNumberHandler<Double> {
    private static final Double ZERO = 0.0;
    private static final DoubleHandler instance = new DoubleHandler();

    private DoubleHandler() {
    }

    public static DoubleHandler getInstance() {
        return instance;
    }

    @Override
    public Double add(Double one, Double two) {
        return one + two;
    }

    @Override
    public boolean greaterOrEqual(Double one, Double two) {
        return one >= two;
    }

    @Override
    public Double getZero() {
        return ZERO;
    }
}
