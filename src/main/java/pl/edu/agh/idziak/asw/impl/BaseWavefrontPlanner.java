package pl.edu.agh.idziak.asw.impl;

import com.google.common.collect.ImmutableSet;
import pl.edu.agh.idziak.asw.model.*;
import pl.edu.agh.idziak.asw.wavefront.Subspace;
import pl.edu.agh.idziak.asw.wavefront.SubspacePlan;
import pl.edu.agh.idziak.asw.wavefront.Wavefront;
import pl.edu.agh.idziak.asw.wavefront.impl.ImmutableSubspace;
import pl.edu.agh.idziak.asw.wavefront.impl.WavefrontImpl;

import java.util.Collections;

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

        Subspace<CS> subspace = ImmutableSubspace.from(Collections.emptySet(), inputPlan.getTargetCollectiveState());

        SubspacePlan<SS, CS> subspacePlan = wavefront
                .buildPlanForSubspace(subspace, inputPlan.getStateSpace(), inputPlan.getCostFunction());

        return ImmutableASWOutputPlan.from(null, ImmutableSet.of(subspacePlan));
    }

}
