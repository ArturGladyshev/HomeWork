package org.example.homework.algorithms;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Integer[] targetArray = {7, 1, 4, 3, 2, 5, 6, 9, 11, 0, 10, 8};
        AlgorithmicUtil.bubbleSort(targetArray);
        System.out.println(Arrays.toString(targetArray));
        for (int i = 0; i != targetArray.length; ++i) {
            System.out.println(AlgorithmicUtil.binarySearch(targetArray, i));
        }

    }
}