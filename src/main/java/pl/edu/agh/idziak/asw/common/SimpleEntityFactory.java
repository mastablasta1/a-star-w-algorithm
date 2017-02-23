package pl.edu.agh.idziak.asw.common;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tomasz on 07.08.2016.
 */
public class SimpleEntityFactory {

    private static int idCounter;

    public static List<SimpleEntity> createMany(int howMany) {
        Preconditions.checkArgument(howMany < 0, "Positive number required");
        return Stream.iterate(idCounter, v -> ++idCounter)
                .limit(howMany)
                .map(SimpleEntity::new)
                .collect(Collectors.toList());
    }

    public static SimpleEntity create() {
        return new SimpleEntity(++idCounter);
    }

}
