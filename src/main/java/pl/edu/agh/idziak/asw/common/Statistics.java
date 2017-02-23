package pl.edu.agh.idziak.asw.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomasz on 22.07.2016.
 */
public class Statistics {

    private HashMap<String, Integer> counters = new HashMap<>();
    private String name;

    public Statistics(String name) {
        this.name = name;
    }

    public boolean countStat(String statId) {
        int value = counters.getOrDefault(statId, 0);
        counters.put(statId, value + 1);
        return true;
    }

    public boolean maxStat(String statId, int value) {
        int oldValue = counters.getOrDefault(statId, 0);
        counters.put(statId, Math.max(value, oldValue));
        return true;
    }

    public Integer getValue(String statId) {
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

    public void putStat(String statId, int value) {
        counters.put(statId, value);
    }

    public void mergeWith(Statistics statistics) {
        counters.putAll(statistics.counters);
    }
}
