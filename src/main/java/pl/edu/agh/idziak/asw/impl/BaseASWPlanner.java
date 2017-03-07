package pl.edu.agh.idziak.asw.impl;

import com.google.common.base.Stopwatch;
import pl.edu.agh.idziak.asw.astar.CollectiveAStarImpl;
import pl.edu.agh.idziak.asw.astar.CollectiveAStarResults;
import pl.edu.agh.idziak.asw.common.Benchmark;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;
import pl.edu.agh.idziak.asw.wavefront.impl.WavefrontImpl;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toSet;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class BaseASWPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final CollectiveAStarImpl<SS, CS, D> collectiveAStar;
    private final Wavefront<SS, CS, D> wavefront;
    private final DeviationZonesFinder<IP, CS> deviationZonesFinder;

    public BaseASWPlanner(AbstractNumberHandler<D> numberHandler, DeviationZonesFinder<IP, CS> deviationZonesFinder) {
        collectiveAStar = new CollectiveAStarImpl<>(numberHandler);
        wavefront = new WavefrontImpl<>(numberHandler);
        this.deviationZonesFinder = deviationZonesFinder;
    }

    @Override public ASWOutputPlan<SS, CS> calculatePlan(IP inputPlan) {
        return calculatePlanWithBenchmark(inputPlan).getOutputPlan();
    }

    public ExtendedOutputPlan<SS,CS> calculatePlanWithBenchmark(IP inputPlan) {
        Benchmark.Builder benchmarkBuilder = Benchmark.newBuilder().algorithmType(AlgorithmType.ASW);

        Stopwatch stopwatch = Stopwatch.createStarted();
        CollectiveAStarResults<CS> results = collectiveAStar.calculatePath(inputPlan, true);
        benchmarkBuilder.openSetSizeLog(results.getStatistics().getSizeOfOpenSetLog());
        benchmarkBuilder.aStarCalculationTimeMs(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.reset().start();
        Set<Subspace<CS>> subspaces = deviationZonesFinder.findDeviationZones(inputPlan, results.getCollectivePath());
        benchmarkBuilder.deviationZonesSearchTimeMs(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        if (subspaces == null) {
            subspaces = Collections.emptySet();
        }

        stopwatch.reset().start();
        Set<SubspacePlan<SS, CS>> devZonePlans =
                subspaces.stream()
                         .map(devZone -> wavefront.buildPlanForSubspace(
                                 devZone,
                                 inputPlan.getStateSpace(),
                                 inputPlan.getCostFunction()))
                         .collect(toSet());
        benchmarkBuilder.wavefrontCalculationTimeMs(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.stop();

        return ExtendedOutputPlan.<SS,CS>newBuilder()
                                 .outputPlan(ImmutableASWOutputPlan.from(results.getCollectivePath(), devZonePlans))
                                 .benchmark(benchmarkBuilder.build())
                                 .build();
    }
}
