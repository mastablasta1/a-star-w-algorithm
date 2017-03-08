package pl.edu.agh.idziak.asw.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import org.junit.Test;
import pl.edu.agh.idziak.asw.impl.grid2d.G2DLightCollectiveState;

import static org.junit.Assert.*;


/**
 * Created by Tomasz on 07.03.2017.
 */
public class DictionaryTest {

    @Test
    public void test1() throws Exception {
        Dictionary<Integer, Integer> dict = new Dictionary<>();

        ImmutableList<Integer> listKey1 = ImmutableList.of(1, 2);
        ImmutableList<Integer> listKey2 = ImmutableList.of(1, 3);
        ImmutableList<Integer> listKey3 = ImmutableList.of(1, 3, 3);

        Integer value1 = 10;
        Integer value2 = 20;
        Integer value3 = 30;

        dict.put(listKey1, value1);
        dict.put(listKey2, value2);
        dict.put(listKey3, value3);

        assertEquals(value1, dict.get(listKey1));
        assertEquals(value2, dict.get(listKey2));
        assertEquals(value3, dict.get(listKey3));
        assertNull(dict.get(ImmutableList.of(9, 9)));
        assertNull(dict.get(ImmutableList.of(1)));
        assertNull(dict.get(ImmutableList.of(1, 2, 3)));
    }

    @Test
    public void refDictTest() throws Exception {
        RefDictionary<Integer, G2DLightCollectiveState> dict = new RefDictionary<>();

        ImmutableList<Integer> listKey1 = ImmutableList.of(1, 2);
        ImmutableList<Integer> listKey2 = ImmutableList.of(1, 3);
        ImmutableList<Integer> listKey3 = ImmutableList.of(1, 3, 3);

        G2DLightCollectiveState value1 = new G2DLightCollectiveState();
        G2DLightCollectiveState value2 = new G2DLightCollectiveState();
        G2DLightCollectiveState value3 = new G2DLightCollectiveState();

        dict.put(listKey1, value1);
        dict.put(listKey2, value2);
        dict.put(listKey3, value3);

        assertEquals(value1, dict.get(listKey1));
        assertEquals(value2, dict.get(listKey2));
        assertEquals(value3, dict.get(listKey3));
        assertNull(dict.get(ImmutableList.of(9, 9)));
        assertNull(dict.get(ImmutableList.of(1)));
        assertNull(dict.get(ImmutableList.of(1, 2, 3)));

        Integer[] iteratedValue1Key = Iterators.toArray(value1.iterate(), Integer.class);
        Object[] expecteds = listKey1.reverse().toArray();
        assertArrayEquals(expecteds,iteratedValue1Key);
    }
}