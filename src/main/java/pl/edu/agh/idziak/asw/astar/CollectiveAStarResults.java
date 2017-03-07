package pl.edu.agh.idziak.asw.astar;

import pl.edu.agh.idziak.asw.model.CollectivePath;
import pl.edu.agh.idziak.asw.model.CollectiveState;

/**
 * Created by Tomasz on 03.03.2017.
 */
public class CollectiveAStarResults<CS extends CollectiveState<?, ?>> {
    private CollectiveAStarStats statistics;
    private CollectivePath<CS> collectivePath;

    public CollectiveAStarResults(CollectivePath<CS> collectivePath, CollectiveAStarStats statistics) {
        this.statistics = statistics;
        this.collectivePath = collectivePath;
    }

    public CollectiveAStarStats getStatistics() {
        return statistics;
    }

    public CollectivePath<CS> getCollectivePath() {
        return collectivePath;
    }
}
