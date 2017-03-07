package pl.edu.agh.idziak.asw.astar;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Comparator.naturalOrder;

/**
 * Created by Tomasz on 03.03.2017.
 */
public class CollectiveAStarStats {

    private List<Integer> sizeOfOpenSetLog = new LinkedList<>();

    public List<Integer> getSizeOfOpenSetLog() {
        return sizeOfOpenSetLog;
    }

    public int getIterationsCount() {
        return sizeOfOpenSetLog.size();
    }

    public Integer maxSizeOfOpenSet() {
        return sizeOfOpenSetLog.stream().max(naturalOrder()).orElseThrow(NoSuchElementException::new);
    }

    void logSizeOfOpenSet(int size) {
        sizeOfOpenSetLog.add(size);
    }
}
