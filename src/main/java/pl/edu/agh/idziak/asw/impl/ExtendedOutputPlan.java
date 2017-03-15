package pl.edu.agh.idziak.asw.impl;

import com.google.common.base.MoreObjects;
import pl.edu.agh.idziak.asw.common.Benchmark;
import pl.edu.agh.idziak.asw.model.CollectiveState;
import pl.edu.agh.idziak.asw.model.ImmutableASWOutputPlan;
import pl.edu.agh.idziak.asw.model.StateSpace;

/**
 * Created by Tomasz on 06.03.2017.
 */
public class ExtendedOutputPlan<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>> {

    private ImmutableASWOutputPlan<SS, CS> outputPlan;
    private Benchmark benchmark;

    private ExtendedOutputPlan(Builder<SS, CS> builder) {
        outputPlan = builder.outputPlan;
        benchmark = builder.benchmark;
    }

    public static <SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>> Builder<SS, CS> newBuilder() {
        return new Builder<>();
    }

    public ImmutableASWOutputPlan<SS, CS> getOutputPlan() {
        return outputPlan;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public static final class Builder<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>> {

        private ImmutableASWOutputPlan<SS, CS> outputPlan;
        private Benchmark benchmark;

        private Builder() {}

        public Builder<SS, CS> outputPlan(ImmutableASWOutputPlan<SS, CS> val) {
            outputPlan = val;
            return this;
        }

        public Builder<SS, CS> benchmark(Benchmark val) {
            benchmark = val;
            return this;
        }

        public ExtendedOutputPlan<SS, CS> build() {
            return new ExtendedOutputPlan<>(this);
        }
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("outputPlan", outputPlan)
                          .add("benchmark", benchmark)
                          .toString();
    }
}
