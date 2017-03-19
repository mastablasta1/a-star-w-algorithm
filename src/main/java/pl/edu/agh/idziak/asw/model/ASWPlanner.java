package pl.edu.agh.idziak.asw.model;

/**
 * Created by Tomasz on 20.02.2017.
 */
public interface ASWPlanner<IP extends InputPlan<SS, CS, ?>, SS extends StateSpace<CS>, CS extends CollectiveState<?>> {
    ASWOutputPlan<SS, CS> calculatePlan(IP inputPlan);
}
