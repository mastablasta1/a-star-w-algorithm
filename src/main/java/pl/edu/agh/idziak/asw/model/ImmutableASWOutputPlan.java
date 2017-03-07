package pl.edu.agh.idziak.asw.model;

import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;

import java.util.Set;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class ImmutableASWOutputPlan<SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>> implements ASWOutputPlan<SS, CS> {

    private final CollectivePath<CS> collectivePath;
    private final Set<SubspacePlan<SS, CS>> subspacePlans;

    public ImmutableASWOutputPlan(CollectivePath<CS> collectivePath, Set<SubspacePlan<SS, CS>> subspacePlans) {
        this.collectivePath = collectivePath;
        if (subspacePlans != null)
            this.subspacePlans = ImmutableSet.copyOf(subspacePlans);
        else
            this.subspacePlans = ImmutableSet.of();
    }

    @Override public CollectivePath<CS> getCollectivePath() {
        return collectivePath;
    }

    public Set<SubspacePlan<SS, CS>> getSubspacePlans() {
        return subspacePlans;
    }

    public static <SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>>
    ImmutableASWOutputPlan<SS, CS> from(CollectivePath<CS> collectivePath,
            Set<SubspacePlan<SS, CS>> subspacePlans) {
        return new ImmutableASWOutputPlan<>(collectivePath, subspacePlans);
    }
}
