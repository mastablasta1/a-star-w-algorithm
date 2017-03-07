package pl.edu.agh.idziak.asw.common;

import com.google.common.base.MoreObjects;
import pl.edu.agh.idziak.asw.impl.AlgorithmType;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Tomasz on 06.03.2017.
 */
public class Benchmark {

    private Long aStarCalculationTimeMs;
    private Long deviationZonesSearchTimeMs;
    private Long wavefrontCalculationTimeMs;
    private List<Integer> openSetSizeLog;
    private Integer maxSizeOfOpenSet;
    private AlgorithmType algorithmType;

    private Benchmark(Builder builder) {
        aStarCalculationTimeMs = builder.aStarCalculationTimeMs;
        deviationZonesSearchTimeMs = builder.deviationZonesSearchTimeMs;
        wavefrontCalculationTimeMs = builder.wavefrontCalculationTimeMs;
        openSetSizeLog = builder.openSetSizeLog;
        if (openSetSizeLog != null)
            maxSizeOfOpenSet = openSetSizeLog.stream().max(Comparator.naturalOrder()).orElse(null);
        algorithmType = builder.algorithmType;
    }

    public Long getAStarCalculationTimeMs() {
        return aStarCalculationTimeMs;
    }

    public Long getDeviationZonesSearchTimeMs() {
        return deviationZonesSearchTimeMs;
    }

    public Long getWavefrontCalculationTimeMs() {
        return wavefrontCalculationTimeMs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<Integer> getOpenSetSizeLog() {
        return openSetSizeLog;
    }

    public Integer getMaxSizeOfOpenSet() {
        return maxSizeOfOpenSet;
    }

    public Integer getIterationCount() {
        if (openSetSizeLog == null)
            return null;
        return openSetSizeLog.size();
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public static final class Builder {

        private Long aStarCalculationTimeMs;
        private Long deviationZonesSearchTimeMs;
        private Long wavefrontCalculationTimeMs;
        private List<Integer> openSetSizeLog;
        private AlgorithmType algorithmType;

        private Builder() {}

        public Benchmark build() {
            return new Benchmark(this);
        }

        public Builder aStarCalculationTimeMs(Long val) {
            aStarCalculationTimeMs = val;
            return this;
        }

        public Builder deviationZonesSearchTimeMs(Long val) {
            deviationZonesSearchTimeMs = val;
            return this;
        }

        public Builder wavefrontCalculationTimeMs(Long val) {
            wavefrontCalculationTimeMs = val;
            return this;
        }

        public Builder openSetSizeLog(List<Integer> val) {
            openSetSizeLog = val;
            return this;
        }

        public Builder algorithmType(AlgorithmType val) {
            algorithmType = val;
            return this;
        }

    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("algorithmType", algorithmType)
                          .add("aStarCalculationTimeMs", aStarCalculationTimeMs)
                          .add("deviationZonesSearchTimeMs", deviationZonesSearchTimeMs)
                          .add("wavefrontCalculationTimeMs", wavefrontCalculationTimeMs)
                          .add("iterationCount", openSetSizeLog.size())
                          .add("maxSizeOfOpenSet", maxSizeOfOpenSet)
                          .toString();
    }
}
