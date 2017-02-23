package pl.edu.agh.idziak.asw.impl.grid2d;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import pl.edu.agh.idziak.asw.common.SimpleEntityFactory;
import pl.edu.agh.idziak.asw.common.Triple;

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
                Triple.of(SimpleEntityFactory.create(), G2DEntityState.of(0, 0), G2DEntityState.of(4, 4))
                // Triple.of(SimpleEntityFactory.create(), G2DEntityState.of(4, 4), G2DEntityState.of(0, 0))
        );
    }

}
