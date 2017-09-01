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

    @Override
    public Double getOne() {
        return ONE;
    }

    @Override
    public boolean lessThan(Double one, Double two) {
        return one < two;
    }
}
