package pl.edu.agh.idziak.asw.common;


import pl.edu.agh.idziak.asw.model.AbstractNumberHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class DoubleHandler implements AbstractNumberHandler<Double> {
    private static final Double ZERO = 0d;
    private static final Double ONE = 1d;
    private static final DoubleHandler INSTANCE = new DoubleHandler();

    private DoubleHandler() {
    }

    public static DoubleHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public boolean greaterOrEqual(Double a, Double b) {
        return a >= b;
    }

    @Override
    public Double getZero() {
        return ZERO;
    }

}
