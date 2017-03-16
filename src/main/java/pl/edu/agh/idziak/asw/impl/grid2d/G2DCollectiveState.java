package pl.edu.agh.idziak.asw.impl.grid2d;

import pl.edu.agh.idziak.asw.model.ImmutableMapCollectiveState;

import java.security.cert.CollectionCertStoreParameters;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class G2DCollectiveState extends ImmutableMapCollectiveState<G2DEntityState, Integer> {

    private final long identity;
    private static final int BITS_PER_STATE = 4;

    public G2DCollectiveState(Map<?, G2DEntityState> entityStates) {
        super(entityStates);
        identity = encodeIdentity(entityStates);
    }

    private long encodeIdentity(Map<?, G2DEntityState> entityStates) {
        long identity = 0;
        int[] ints = entityStates.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().hashCode()))
                .mapToInt(value -> (value.getValue().getRow() << BITS_PER_STATE)
                        | value.getValue().getCol())
                .toArray();
        for (int anInt : ints) {
            identity = (identity << (BITS_PER_STATE * 2)) | anInt;
        }

//        for (Map.Entry<?, G2DEntityState> entry : entityStates.entrySet()) {
//            identity = (identity << (BITS_PER_STATE * 2))
//                    | (entry.getValue().getRow() << BITS_PER_STATE)
//                    | entry.getValue().getCol();
//        }
        return identity;
    }

    public static G2DCollectiveState from(Map<?, G2DEntityState> entityStateMap) {
        return new G2DCollectiveState(entityStateMap);
    }

//    @Override public boolean equals(Object o) {
//        return this == o
//                || !(o == null || getClass() != o.getClass())
//                && ((G2DCollectiveState) o).identity == identity;
//    }

}
