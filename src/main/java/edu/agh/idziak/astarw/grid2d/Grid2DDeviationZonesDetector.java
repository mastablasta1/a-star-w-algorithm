package edu.agh.idziak.astarw.grid2d;

import edu.agh.idziak.astarw.*;
import edu.agh.idziak.astarw.algorithm.PlanningData;
import edu.agh.idziak.common.Pair;
import edu.agh.idziak.common.PairCombinator;
import edu.agh.idziak.common.PairIterator;
import edu.agh.idziak.common.Utils;

import java.util.*;

/**
 * Created by Tomasz on 18.07.2016.
 */
public class Grid2DDeviationZonesDetector implements DeviationZonesDetector<Grid2DStateSpace, Grid2DGlobalState, Integer, Double> {

    private int minSafeDistance;

    public Grid2DDeviationZonesDetector(int minSafeDistance) {
        this.minSafeDistance = minSafeDistance;
    }

    @Override
    public List<EntityOutputPlan<Integer>> detectDeviationZones(PlanningData<Grid2DStateSpace, Grid2DGlobalState, Integer, Double> planningData) {
        int entitiesCount = planningData.getInputPlan().getEntitiesCount();
        GlobalPath<Integer> globalPath = planningData.getGlobalPath();

        List<List<EntityState<Integer>>> paths
                = Utils.fillList(new ArrayList<>(entitiesCount), entitiesCount, ArrayList::new);

        extractPaths(paths, globalPath);

        List<List<DeviationZone<Integer>>> deviationZones = findDeviationZones(planningData);

        Iterator<List<EntityState<Integer>>> pathsIt = paths.iterator();
        Iterator<List<DeviationZone<Integer>>> devZonesIt = deviationZones.iterator();

        return Utils.fillList(
                new ArrayList<>(entitiesCount),
                entitiesCount,
                () -> new Grid2DEntityOutputPlan<>(devZonesIt.next(), pathsIt.next()));

    }

    private void extractPaths(List<List<EntityState<Integer>>> paths, GlobalPath<Integer> globalPath) {
        for (GlobalState<Integer> globalState : globalPath.get()) {
            PairIterator<EntityState<Integer>, List<EntityState<Integer>>> it =
                    new PairIterator<>(globalState.getEntityStates(), paths);
            while (it.hasNext()) {
                Pair<EntityState<Integer>, List<EntityState<Integer>>> next = it.next();
                next.getTwo().add(next.getOne());
            }
        }
    }

    private List<List<DeviationZone<Integer>>> findDeviationZones(PlanningData<Grid2DStateSpace, Grid2DGlobalState, Integer, Double> planningData) {

        Grid2DStateSpace stateSpace = planningData.getInputPlan().getStateSpace();
        int size = planningData.getInputPlan().getEntitiesCount();


        for (GlobalState<Integer> globalState : planningData.getGlobalPath().get()) {
//            List<List<EntityState<Integer>>> proximateStates = Utils.fillList(new ArrayList<>(), size, ArrayList::new);
            List<DeviationZone<Integer>> deviationZones = new ArrayList<>();
            PairCombinator<EntityState<Integer>> comb = new PairCombinator<>(globalState.getEntityStates());

            while (comb.hasNext()) {
                PairCombinator.Entry<EntityState<Integer>> pair = comb.next();
                if (areEntitiesProximate(stateSpace, pair)) {
                    deviationZones.add(createDeviationZoneBetween());
                }
            }


        }

        return null;
    }

    private boolean areEntitiesProximate(Grid2DStateSpace stateSpace, PairCombinator.Entry<EntityState<Integer>> pair) {
        return stateSpace.getHeuristicDistance(pair.getFirstElement(), pair.getSecondElement()) < minSafeDistance;
    }
}
