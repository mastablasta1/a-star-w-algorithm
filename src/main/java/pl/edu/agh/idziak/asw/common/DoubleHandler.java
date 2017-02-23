package pl.edu.agh.idziak.asw.common;


import pl.edu.agh.idziak.asw.model.AbstractNumberHandler;

/**
 * Created by Tomasz on 09.07.2016.
 */
public class DoubleHandler implements AbstractNumberHandler<Double> {
    private static final Double ZERO = 0.0;
    private static final Double ONE = 1.0;
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

    @Override
    public Double getOne() {
        return ONE;
    }

    @Override
    public boolean lessThan(Double one, Double two) {
        return one < two;
    }
}
