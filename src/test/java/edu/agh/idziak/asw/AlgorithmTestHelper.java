package edu.agh.idziak.asw;

import edu.agh.idziak.asw.grid2d.G2DEntityState;
import edu.agh.idziak.asw.grid2d.SimpleEntityFactory;
import edu.agh.idziak.common.Triple;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class AlgorithmTestHelper {
    public static Set mapToEntitiesSet(List<Triple<SimpleEntityFactory.SimpleEntity, G2DEntityState, G2DEntityState>> input) {
        return input.stream().map(Triple::getOne).collect(Collectors.toSet());
    }

    public static <ES> Map<?, ES> mapToInitialState(List<Triple<SimpleEntityFactory.SimpleEntity, ES, ES>> input) {
        return input.stream().collect(Collectors.toMap(Triple::getOne, Triple::getTwo));
    }

    public static <ES> Map<?, ES> mapToTargetState(List<Triple<SimpleEntityFactory.SimpleEntity, ES, ES>> input) {
        return input.stream().collect(Collectors.toMap(Triple::getOne, Triple::getThree));
    }
}
