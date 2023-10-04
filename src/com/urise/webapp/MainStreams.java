package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] numbers1 = {1, 2, 3};
        int[] numbers2 = {9, 3, 8, 8, 1, 9, 5};
        System.out.println("minValue: " + minValue(numbers1));
        System.out.println("minValue: " + minValue(numbers2));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(7);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(7);
        System.out.println("oddOrEven: " + oddOrEven(list));
        System.out.println("oddOrEven: " + oddOrEven(list2));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((acc, num) -> acc * 10 + num).orElseThrow(NoSuchElementException::new);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        AtomicInteger sum = new AtomicInteger();
        return integers.stream()
                .peek(sum::addAndGet)
                .collect(Collectors.partitioningBy(n -> n % 2 == 0))
                .get(sum.get() % 2 != 0);
    }
}
