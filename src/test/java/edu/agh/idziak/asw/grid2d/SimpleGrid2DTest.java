package edu.agh.idziak.asw.grid2d;

import com.google.common.collect.ImmutableList;
import edu.agh.idziak.common.SimpleEntityFactory;
import edu.agh.idziak.common.Triple;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tomasz on 17.07.2016.
 */
public class SimpleGrid2DTest extends BaseGrid2DTest {

    @Test
    public void test1() {
        executeGrid2DTest(createPositionsAndTargets(), createStateSpace());
    }

    private static int[][] createStateSpace() {
        return new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
    }

    private static List<Triple<?, G2DEntityState, G2DEntityState>> createPositionsAndTargets() {
        return ImmutableList.of(
                Triple.of(SimpleEntityFactory.create(), G2DEntityState.of(0, 0), G2DEntityState.of(4, 4)),
                Triple.of(SimpleEntityFactory.create(), G2DEntityState.of(4, 4), G2DEntityState.of(0, 0))
        );
    }

}
