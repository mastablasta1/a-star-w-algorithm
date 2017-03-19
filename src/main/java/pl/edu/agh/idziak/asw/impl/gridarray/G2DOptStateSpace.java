package pl.edu.agh.idziak.asw.impl.gridarray;

import pl.edu.agh.idziak.asw.common.Utils;
import pl.edu.agh.idziak.asw.model.StateSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Tomasz on 29.06.2016.
 */
public class G2DOptStateSpace implements StateSpace<G2DOptCollectiveState> {

    private final byte[][] space;
    private final int rows;
    private final int cols;
    private final boolean[][][][] stateChangeCache;
    private OptimizedArrayList<byte[]> neighborStateArraysCache;
    private final boolean[][] stateUniquenessCache;
    private static final int MOORE_NEIGHBORS_COUNT = 9;
    private static final int VON_NEUMANN_NEIGHBORS_COUNT = 5;
    private final Neighborhood neighborhood = Neighborhood.MOORE; // FIXME parametrize

    public enum Neighborhood {
        VON_NEUMANN, MOORE
    }

    public G2DOptStateSpace(byte[][] space) {
        this.space = Utils.arrayCopy(checkNotNull(space));
        this.rows = space.length;
        this.cols = space[0].length;
        stateChangeCache = new boolean[rows][][][];
        stateUniquenessCache = new boolean[rows][cols];
        neighborStateArraysCache = new OptimizedArrayList<>();
    }

    public List<G2DOptCollectiveState> getNeighborStatesOf(G2DOptCollectiveState collectiveState) {
        byte[] thisStateArray = collectiveState.getState();
        int colStatesCount = thisStateArray.length / 2;
        List<G2DOptCollectiveState> outputNeighborStates = new ArrayList<>(numOfNeighborsOfCollectiveState(colStatesCount));

        List<List<byte[]>> combinatorialArray = buildCombinatorialArray(thisStateArray);

        generateCombinations(combinatorialArray);

        int neighborsCount = neighborStateArraysCache.size();

        outerLoop:
        for (int i = 0; i < neighborsCount; i++) {
            byte[] neighborStateArray = neighborStateArraysCache.get(i);

            clearStateChangeCache();

            for (int j = 0; j < neighborStateArray.length; j += 2) {
                byte fromRow = thisStateArray[j];
                byte fromCol = thisStateArray[j + 1];
                byte toRow = neighborStateArray[j];
                byte toCol = neighborStateArray[j + 1];

                if (searchCache(toRow, toCol, fromRow, fromCol)) {
                    continue outerLoop; // collision detected, skip this neighbor
                }
                putIntoCache(fromRow, fromCol, toRow, toCol);
            }

            G2DOptCollectiveState newNeighborState = new G2DOptCollectiveState(neighborStateArray);
            outputNeighborStates.add(newNeighborState);
        }


        return outputNeighborStates;
    }

    private int numOfNeighborsOfCollectiveState(int colStatesCount) {
        return (int) Math.pow(getNeighborsCount(), colStatesCount);
    }

    public void generateCombinations(List<List<byte[]>> combinatorialArray) {
        int collectiveStatesCount = combinatorialArray.size();
        int requiredCapacity = (int) Math.pow(getNeighborsCount(), collectiveStatesCount);
        neighborStateArraysCache.clear();
        if (requiredCapacity > neighborStateArraysCache.getCapacity()) {
            neighborStateArraysCache.resizeAndClear(requiredCapacity);
        }

        byte[] currentCombination = initializeFirstCombination(combinatorialArray, collectiveStatesCount);

        int[] verticalPositions = new int[collectiveStatesCount];
        int horizPos = 0;
        while (true) {
            List<byte[]> vertList = combinatorialArray.get(horizPos);
            int newVertPos = ++verticalPositions[horizPos];
            if (newVertPos < vertList.size()) {
                byte[] entityState = vertList.get(newVertPos);
                currentCombination[2 * horizPos] = entityState[0];
                currentCombination[2 * horizPos + 1] = entityState[1];
                byte[] newCombination = currentCombination.clone();
                if (areAllEntityStatesUnique(newCombination)) {
                    neighborStateArraysCache.add(newCombination);
                }
                horizPos = 0;
            } else {
                verticalPositions[horizPos] = 0;
                currentCombination[2 * horizPos] = vertList.get(0)[0];
                currentCombination[2 * horizPos + 1] = vertList.get(0)[1];
                if (++horizPos >= verticalPositions.length) {
                    break;
                }
            }
        }
    }

    private byte[] initializeFirstCombination(List<List<byte[]>> combinatorialArray, int collectiveStatesCount) {
        byte[] currentCombination = new byte[collectiveStatesCount * 2];

        // initialize first combination
        for (int i = 0; i < collectiveStatesCount; i++) {
            byte[] entityState = combinatorialArray.get(i).get(0);
            currentCombination[2 * i] = entityState[0];
            currentCombination[2 * i + 1] = entityState[1];
        }
        if (areAllEntityStatesUnique(currentCombination)) {
            neighborStateArraysCache.add(currentCombination);
        }
        return currentCombination;
    }

    private void clearStateChangeCache() {
        for (int i = 0; i < rows; i++) {
            stateChangeCache[i] = null;
        }
    }


    private List<List<byte[]>> buildCombinatorialArray(byte[] states) {
        List<List<byte[]>> output = new ArrayList<>(states.length);
        int colStatesCount = states.length;
        for (int i = 0; i < colStatesCount; i += 2) {
            output.add(getNeighborStatesOf(states[i], states[i + 1]));
        }
        return output;
    }

    private boolean areAllEntityStatesUnique(byte[] states) {
        Utils.zeroArray(stateUniquenessCache);

        for (int i = 0; i < states.length; i += 2) {
            byte row = states[i];
            byte col = states[i + 1];
            if (stateUniquenessCache[row][col]) {
                return false;
            } else {
                stateUniquenessCache[row][col] = true;
            }
        }
        return true;
    }

    public ArrayList<byte[]> getNeighborStatesOf(byte row, byte col) {
        ArrayList<byte[]> states = new ArrayList<>(getNeighborsCount());

        addState(row, col, states);
        if (col > 0) addState(row, (byte) (col - 1), states);
        if (col < cols - 1) addState(row, (byte) (col + 1), states);

        if (row > 0) {
            addState((byte) (row - 1), col, states);
            if (col > 0) addState((byte) (row - 1), (byte) (col - 1), states);
            if (col < cols - 1) addState((byte) (row - 1), (byte) (col + 1), states);
        }

        if (row < rows - 1) {
            addState((byte) (row + 1), col, states);
            if (col > 0) addState((byte) (row + 1), (byte) (col - 1), states);
            if (col < cols - 1) addState((byte) (row + 1), (byte) (col + 1), states);
        }


        return states;
    }

    private void addState(byte destRow, byte destCol, ArrayList<byte[]> states) {
        byte weight = space[destRow][destCol];
        if (weight <= 0) {
            states.add(new byte[]{destRow, destCol});
        }
    }

    public byte[][] getGridArray() {
        return space;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte[] row : space) {
            sb.append(Arrays.toString(row))
                    .append("\n");
        }
        return sb.toString();
    }

    private boolean searchCache(int fromRow, int fromCol, int toRow, int toCol) {
        boolean[][][] level1 = stateChangeCache[fromRow];
        if (level1 == null) return false;
        boolean[][] level2 = level1[fromCol];
        if (level2 == null) return false;
        boolean[] level3 = level2[toRow];
        if (level3 == null) return false;
        return level3[toCol];
    }


    private void putIntoCache(int fromRow, int fromCol, int toRow, int toCol) {
        boolean[][][] level1 = stateChangeCache[fromRow];
        if (level1 == null) level1 = stateChangeCache[fromRow] = new boolean[cols][][];
        boolean[][] level2 = level1[fromCol];
        if (level2 == null) level2 = stateChangeCache[fromRow][fromCol] = new boolean[rows][];
        boolean[] level3 = level2[toRow];
        if (level3 == null) level3 = stateChangeCache[fromRow][fromCol][toRow] = new boolean[cols];
        level3[toCol] = true;
    }

    private int getNeighborsCount() {
        switch (neighborhood) {
            case VON_NEUMANN:
                return VON_NEUMANN_NEIGHBORS_COUNT;
            case MOORE:
                return MOORE_NEIGHBORS_COUNT;
        }
        throw new IllegalStateException("Should not happen, unknown neighborhood");
    }

}
