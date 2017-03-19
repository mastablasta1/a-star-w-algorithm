package pl.edu.agh.idziak.asw.impl;

import com.google.common.base.Stopwatch;
import pl.edu.agh.idziak.asw.astar.CollectiveAStarImpl;
import pl.edu.agh.idziak.asw.astar.CollectiveAStarResults;
import pl.edu.agh.idziak.asw.common.Benchmark;
import pl.edu.agh.idziak.asw.model.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class BaseAStarPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends StateSpace<CS>, CS extends CollectiveState<?>, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final CollectiveAStarImpl<SS, CS, D> collectiveAStar;

    public BaseAStarPlanner(AbstractNumberHandler<D> numberHandler) {
        collectiveAStar = new CollectiveAStarImpl<>(numberHandler);
    }

    @Override public ASWOutputPlan<SS, CS> calculatePlan(IP inputPlan) {
        return calculatePlanWithBenchmark(inputPlan).getOutputPlan();
    }

    public ExtendedOutputPlan<SS, CS> calculatePlanWithBenchmark(IP inputPlan) {
        Benchmark.Builder benchmarkBuilder = Benchmark.newBuilder().algorithmType(AlgorithmType.ASTAR_ONLY);

        Stopwatch stopwatch = Stopwatch.createStarted();
        CollectiveAStarResults<CS> results = collectiveAStar.calculatePath(inputPlan, true);
        benchmarkBuilder.openSetSizeLog(results.getStatistics().getSizeOfOpenSetLog());
        benchmarkBuilder.aStarCalculationTimeMs(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return ExtendedOutputPlan.<SS, CS>newBuilder()
                .outputPlan(ImmutableASWOutputPlan.from(results.getCollectivePath(), null))
                .benchmark(benchmarkBuilder.build())
                .build();
    }

}
