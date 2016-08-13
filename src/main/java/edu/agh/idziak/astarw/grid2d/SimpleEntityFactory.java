package edu.agh.idziak.astarw.grid2d;

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

    public static class SimpleEntity {
        private int id;

        SimpleEntity(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleEntity that = (SimpleEntity) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return Integer.toString(id);
        }
    }
}
