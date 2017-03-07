package pl.edu.agh.idziak.asw.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomasz on 22.07.2016.
 */
public class Statistics {

    private HashMap<String, Integer> counters = new HashMap<>();
    private String name;
    private HashMap<String, List<Integer>> integerListStats = new HashMap<>();
    private HashMap<String, String> info = new HashMap<>();

    public Statistics(String name) {
        this.name = name;
    }

    protected boolean countStat(String statId) {
        int value = counters.getOrDefault(statId, 0);
        counters.put(statId, value + 1);
        return true;
    }

    protected boolean maxStat(String statId, int value) {
        int oldValue = counters.getOrDefault(statId, 0);
        counters.put(statId, Math.max(value, oldValue));
        return true;
    }

    protected void logListable(String statId, Integer value) {
        integerListStats.get(statId);
    }

    protected Integer getValue(String statId) {
        return counters.get(statId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Statistics")
          .append("(")
          .append(name)
          .append(")")
          .append("{\n");
        counters.forEach((statId, value) -> {
            sb.append(statId).append("=").append(value).append(",\n");
        });
        return sb.append("}").toString();
    }

    public Map<String, Integer> getAsMap() {
        return new HashMap<>(counters);
    }

    public void putStat(String statId, Integer value) {
        counters.put(statId, value);
    }

    public void mergeWith(Statistics statistics) {
        counters.putAll(statistics.counters);
    }

    public void putInfo(String id, String value) {
        info.put(id, value);
    }
}
