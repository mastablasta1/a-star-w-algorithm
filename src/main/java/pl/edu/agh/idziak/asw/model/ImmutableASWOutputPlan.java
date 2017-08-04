package pl.edu.agh.idziak.asw.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.wavefront.DeviationSubspacePlan;

import java.util.Set;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class ImmutableASWOutputPlan<SS extends CollectiveStateSpace<CS>, CS extends CollectiveState<?>> implements ASWOutputPlan<SS, CS> {

    private final CollectivePath<CS> collectivePath;
    private final Set<DeviationSubspacePlan<CS>> deviationSubspacePlans;

    public ImmutableASWOutputPlan(CollectivePath<CS> collectivePath, Set<DeviationSubspacePlan<CS>> deviationSubspacePlans) {
        this.collectivePath = collectivePath;
        if (deviationSubspacePlans != null)
            this.deviationSubspacePlans = ImmutableSet.copyOf(deviationSubspacePlans);
        else
            this.deviationSubspacePlans = ImmutableSet.of();
    }

    @Override public CollectivePath<CS> getCollectivePath() {
        return collectivePath;
    }

    public Set<DeviationSubspacePlan<CS>> getDeviationSubspacePlans() {
        return deviationSubspacePlans;
    }

    public static <SS extends CollectiveStateSpace<CS>, CS extends CollectiveState<?>>
    ImmutableASWOutputPlan<SS, CS> from(CollectivePath<CS> collectivePath,
            Set<DeviationSubspacePlan<CS>> deviationSubspacePlans) {
        return new ImmutableASWOutputPlan<>(collectivePath, deviationSubspacePlans);
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("collectivePath", collectivePath)
                          .add("subspacePlansCount", deviationSubspacePlans.size())
                          .toString();
    }
}
