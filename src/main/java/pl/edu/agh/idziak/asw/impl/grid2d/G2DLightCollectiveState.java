package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.common.RefDictionary;

import java.util.Iterator;

/**
 * Created by Tomasz on 08.03.2017.
 */
public class G2DLightCollectiveState implements RefDictionary.IterableAcceptor<Integer> {

    private Iterable<Integer> reference;

    public Iterator<Integer> iterate() {
        return reference.iterator();
    }

    @Override public void acceptIterableKey(Iterable<Integer> iterable) {
        reference = iterable;
    }
}
