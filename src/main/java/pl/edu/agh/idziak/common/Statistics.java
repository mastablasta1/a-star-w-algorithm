package pl.edu.agh.idziak.common;

import java.util.HashMap;

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
}
