package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.impl.AlgorithmType;
import pl.edu.agh.idziak.asw.model.ASWOutputPlan;
import pl.edu.agh.idziak.asw.model.ImmutableASWOutputPlan;

public class GridASWOutputPlan extends ImmutableASWOutputPlan<GridCollectiveStateSpace, GridCollectiveState> {

    private final AlgorithmType algorithmType;

    public GridASWOutputPlan(ASWOutputPlan<GridCollectiveState> origPlan,
                             AlgorithmType algorithmType) {
        super(origPlan.getCollectivePath(), origPlan.getDeviationSubspacePlans());
        this.algorithmType = algorithmType;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }
}
