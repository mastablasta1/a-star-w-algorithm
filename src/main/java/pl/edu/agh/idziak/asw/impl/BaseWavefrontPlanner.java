package pl.edu.agh.idziak.asw.impl;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.common.Benchmark;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;
import pl.edu.agh.idziak.asw.wavefront.impl.WavefrontImpl;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class BaseWavefrontPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final Wavefront<SS, CS, D> wavefront;

    public BaseWavefrontPlanner(AbstractNumberHandler<D> numberHandler) {
        wavefront = new WavefrontImpl<>(numberHandler);
    }

    @Override public ASWOutputPlan<SS, CS> calculatePlan(IP inputPlan) {
        return calculatePlanWithBenchmark(inputPlan).getOutputPlan();
    }

    public ExtendedOutputPlan<SS, CS> calculatePlanWithBenchmark(IP inputPlan) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SubspacePlan<CS> subspacePlan = wavefront.buildPlanForEntireSpace(
                inputPlan.getTargetCollectiveState(),
                inputPlan.getStateSpace(),
                inputPlan.getCostFunction());
        stopwatch.stop();
        Benchmark benchmark = Benchmark.newBuilder()
                                       .wavefrontCalculationTimeMs(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                                       .algorithmType(AlgorithmType.WAVEFRONT)
                                       .build();
        CollectivePath<CS> collectivePath = subspacePlan.constructPath(
                inputPlan.getInitialCollectiveState(),
                inputPlan.getTargetCollectiveState());

        return ExtendedOutputPlan.<SS, CS>newBuilder()
                .outputPlan(ImmutableASWOutputPlan.from(collectivePath, ImmutableSet.of(subspacePlan)))
                .benchmark(benchmark)
                .build();
    }
}
