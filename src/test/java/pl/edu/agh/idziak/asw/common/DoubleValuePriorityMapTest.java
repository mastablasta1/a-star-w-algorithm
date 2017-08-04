package pl.edu.agh.idziak.asw.common;

import org.junit.Test;
import pl.edu.agh.idziak.asw.astar.DoubleValuePriorityMap;
import pl.edu.agh.idziak.asw.astar.DoubleValuePriorityMapImpl;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tomasz on 09.03.2017.
 */
public class DoubleValuePriorityMapTest {

    @Test
    public void noReverseOrder() throws Exception {
        DoubleValuePriorityMap<String, Integer> queue = new DoubleValuePriorityMapImpl<>(false);
        String key1 = "one";
        String key2 = "two";
        String key3 = "three";
        String key4 = "four";
        queue.add(key1, 2, 20);
        queue.add(key2, 2, 10);
        queue.add(key3, 0, 3);
        queue.add(key4, 0, 4);

        assertEquals(key3, queue.pollFirstKey());
        assertEquals(key4, queue.pollFirstKey());
        assertEquals(key2, queue.pollFirstKey());
        assertEquals(key1, queue.pollFirstKey());
    }

    @Test
    public void reverseOrder() throws Exception {
        DoubleValuePriorityMap<String, Integer> queue = new DoubleValuePriorityMapImpl<>(true);
        String key1 = "one";
        String key2 = "two";
        String key3 = "three";
        String key4 = "four";
        queue.add(key1, 2, 20);
        queue.add(key2, 2, 10);
        queue.add(key3, 0, 3);
        queue.add(key4, 0, 4);

        assertEquals(key4, queue.pollFirstKey());
        assertEquals(key3, queue.pollFirstKey());
        assertEquals(key1, queue.pollFirstKey());
        assertEquals(key2, queue.pollFirstKey());
    }
}