package pl.edu.agh.idziak.asw.astar;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomasz on 03.03.2017.
 */
public class CollectiveAStarStats {

    private List<Integer> sizeOfOpenSetLog = new LinkedList<>();
    private int maxSizeOfOpenSet = 0;

    public List<Integer> getSizeOfOpenSetLog() {
        return sizeOfOpenSetLog;
    }

    public int getIterationsCount() {
        return sizeOfOpenSetLog.size();
    }

    public Integer maxSizeOfOpenSet() {
        return maxSizeOfOpenSet;
    }

    void logSizeOfOpenSet(int size) {
        sizeOfOpenSetLog.add(size);
        maxSizeOfOpenSet = size > maxSizeOfOpenSet ? size : maxSizeOfOpenSet;
    }
}
