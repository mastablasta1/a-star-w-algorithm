package pl.edu.agh.idziak.asw.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tomasz on 09.03.2017.
 */
public class ValueSortedPriorityQueueTest {

    @Test
    public void test1() throws Exception {
        ValueSortedPriorityQueue<String, Integer> queue = new ValueSortedPriorityQueue<>();
        String key1 = "one";
        String key2 = "two";
        String key3 = "three";
        String key4 = "four";
        queue.add(key1, 2);
        queue.add(key2, 2);
        queue.add(key3, 0);
        queue.add(key4, 0);

        queue.pollFirst();
        assertEquals(key4, queue.getFirstKey());
        queue.pollFirst();
        assertEquals(key3, queue.getFirstKey());
        queue.pollFirst();
        assertEquals(key2, queue.getFirstKey());
    }
}