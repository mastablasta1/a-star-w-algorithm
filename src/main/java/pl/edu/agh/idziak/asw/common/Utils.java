package pl.edu.agh.idziak.asw.common;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Created by Tomasz on 05.03.2017.
 */
public class Utils {

    public static <T, U> void forEachParallel(Iterable<T> iterable1, Iterable<U> iterable2, BiConsumer<T, U> consumer) {
        Iterator<T> it1 = iterable1.iterator();
        Iterator<U> it2 = iterable2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            consumer.accept(it1.next(), it2.next());
        }
    }

    public static <T, U> boolean interruptableForEach(Iterable<T> iterable1, Iterable<U> iterable2, BiFunction<T, U, Boolean> function) {
        Iterator<T> it1 = iterable1.iterator();
        Iterator<U> it2 = iterable2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!function.apply(it1.next(), it2.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> T lastElementOf(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static int[][] arrayCopy(int[][] existingArray) {
        int[][] newArray = new int[existingArray.length][];
        for (int i = 0; i < existingArray.length; i++) {
            int rowLength = existingArray[i].length;
            newArray[i] = new int[rowLength];
            System.arraycopy(existingArray[i], 0, newArray[i], 0, rowLength);
        }
        return newArray;
    }

    public static byte[] arrayCopy(byte[] array) {
        byte[] newArray = new byte[array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public static short[][] arrayCopy(short[][] existingArray) {
        short[][] newArray = new short[existingArray.length][];
        for (short i = 0; i < existingArray.length; i++) {
            int rowLength = existingArray[i].length;
            newArray[i] = new short[rowLength];
            System.arraycopy(existingArray[i], 0, newArray[i], 0, rowLength);
        }
        return newArray;
    }

    public static byte[][] arrayCopy(byte[][] existingArray) {
        byte[][] newArray = new byte[existingArray.length][];
        for (byte i = 0; i < existingArray.length; i++) {
            int rowLength = existingArray[i].length;
            newArray[i] = new byte[rowLength];
            System.arraycopy(existingArray[i], 0, newArray[i], 0, rowLength);
        }
        return newArray;
    }

    public static byte[][] toByteArray(int[][] existingArray) {
        byte[][] newArray = new byte[existingArray.length][];
        for (byte i = 0; i < existingArray.length; i++) {
            int rowLength = existingArray[i].length;
            newArray[i] = new byte[rowLength];
            for (int j = 0; j < rowLength; j++) {
                newArray[i][j] = (byte) existingArray[i][j];
            }
        }
        return newArray;
    }

    public static void clearArray(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            int length = array[i].length;
            for (int j = 0; j < length; j++) {
                array[i][j] = false;
            }
        }
    }

    public static int[][] toIntegerArray(byte[][] existingArray) {
        int[][] newArray = new int[existingArray.length][];
        for (byte i = 0; i < existingArray.length; i++) {
            int rowLength = existingArray[i].length;
            newArray[i] = new int[rowLength];
            for (int j = 0; j < rowLength; j++) {
                newArray[i][j] = existingArray[i][j];
            }
        }
        return newArray;
    }
}
