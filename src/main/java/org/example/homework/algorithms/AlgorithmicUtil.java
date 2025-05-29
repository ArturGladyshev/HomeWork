package org.example.homework.algorithms;

public class AlgorithmicUtil {

    public static <T extends Comparable<? super T>> void bubbleSort(T[] array) {
        for (int i = array.length - 1; i != 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T val = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = val;
                }
            }
        }
    }

    public static <T extends Comparable<? super T>> T binarySearch(T[] array, T targetVal) {
        return targetVal.compareTo(array[0]) == 0 ? array[0]
                : (targetVal.compareTo(array[array.length - 1]) == 0 ? array[array.length - 1]
                : binarySearchInRange(array, targetVal, 0, array.length - 1));
    }

    public static <T extends Comparable<? super T>> T binarySearchInRange(T[] array, T targetVal,
                                                                          int beginIndex, int lastIndex) {
        int divider = ((lastIndex - beginIndex) / 2) < 1 ? beginIndex + 1
                : (lastIndex - beginIndex) / 2 + beginIndex;
        int resultCompare = targetVal.compareTo(array[divider]);
        if (resultCompare != 0) {
            if (resultCompare > 0) {
                return binarySearchInRange(array, targetVal, divider, lastIndex);
            } else {
                return binarySearchInRange(array, targetVal, beginIndex, divider);
            }
        }
        return array[divider];
    }

}