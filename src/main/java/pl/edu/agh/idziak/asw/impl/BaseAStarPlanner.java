package pl.edu.agh.idziak.asw.impl;

import pl.edu.agh.idziak.asw.astar.CollectiveAStar;
import pl.edu.agh.idziak.asw.model.*;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class BaseAStarPlanner<IP extends InputPlan<SS, CS, D>,
        SS extends StateSpace<CS>, CS extends CollectiveState<?, ?>, D extends Comparable<D>>
        implements ASWPlanner<IP, SS, CS> {

    private final CollectiveAStar<SS, CS, D> collectiveAStar;

    public BaseAStarPlanner(AbstractNumberHandler<D> numberHandler) {
        collectiveAStar = new CollectiveAStar<>(numberHandler);
    }

    @Override public ASWOutputPlan<SS, CS> calculatePlan(IP inputPlan) {
        CollectivePath<CS> collectivePath = collectiveAStar.calculatePath(inputPlan);
        return ImmutableASWOutputPlan.from(collectivePath, null);
    }

}
