package com.pioneers.functionalprogramming.streams;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        final int sum = numbers.stream()
                .filter(Main::isEven)
//                .reduce(1, Main::multiply);
        .mapToInt(Main::addOne)
                .sum();


        System.out.println("sum = " + sum);
    }

    public static int addOne(final int num) {
        return num + 1;
    }

    public static int sum(final int num1, final int num2) {
        return num1 + num2;
    }

    public static int multiply(final int num1, final int num2) {
        return num1 * num2;
    }

    private static boolean isEven(final int number) {
        return number % 2 == 0;
    }

    private static boolean isOdd(final int number) {
        return number % 2 != 0;
    }
}
