package edu.agh.idziak.astarw;

/**
 * Created by Tomasz on 28.06.2016.
 */
public interface EntityBasicPlan<E,P> {
    E getEntity();
    P getInitialPosition();
    P getTargetPosition();
}
