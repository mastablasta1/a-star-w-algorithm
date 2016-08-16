package edu.agh.idziak.asw;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by Tomasz on 29.06.2016.
 */

public interface CollectivePath<ES extends EntityState<P>, P extends Comparable<P>> {
    List<CollectiveState<ES, P>> get();

    /**
     * Casts path of {@link CollectiveState} to given type. Method will throw exception if accual type of collective states does not match given class parameter.
     *
     * @param clazz specific class to which list is cast
     * @param <T>   type extending {@link CollectiveState}
     * @return parameterized list of {@link CollectiveState}
     */
    @SuppressWarnings("unchecked")
    default <T extends CollectiveState<ES, P>> List<T> get(Class<T> clazz) {
        List<CollectiveState<ES, P>> collectiveStates = get();
        for (CollectiveState<ES, P> state : collectiveStates) {
            if (!clazz.isAssignableFrom(state.getClass())) {
                throw new ClassCastException(format("Could not parametrize path. Type %s is not subclass of %s.", state.getClass(), clazz));
            }
        }

        return (List<T>) collectiveStates;
    }

    List<EntityState<P>> getPathFor(Object entity);
}
