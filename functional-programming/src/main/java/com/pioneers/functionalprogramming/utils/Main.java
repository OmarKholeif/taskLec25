package com.pioneers.functionalprogramming.utils;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        /*final Scanner scanner = new Scanner(System.in);
        final float num1 = scanner.nextFloat();
        final float num2 = scanner.nextFloat();

//        final int result = processIntegers((num1, num2) -> num1 + num2);
        final float result = processIntegers(Calculator::multiply, num1, num2);
        System.out.println("result = " + result);*/

        final Employee employee1 = new Employee("Mostafa", "Desouky", 25, "mostafa.desouky@techpioneershu.com");
        final Employee employee2 = new Employee("Mahmoud", "Shehata", 24, "mahmoud.shehata@techpioneershu.com");

        final String employeeResult = processEmployee(Main::returnEmployeeInfo, employee1);
        System.out.println("employeeResult = " + employeeResult);

        final Function<String, String> function = toFunction(Main::buildFullName, employee1);

        final String info = toString(function, "the");
        System.out.println("info = " + info);
    }

    private static float processIntegers(final ToIntBiIntFunction<Float> function, final float num1, final float num2) {
        return function.apply(num1, num2);
    }

    public static String processEmployee(final ToStringFromEmployee function, final Employee employee) {
        return function.apply(employee);
    }

    public static String buildFullName(final Employee employee) {
        return employee.firstName() + " " + employee.secondName();
    }

    public static String returnEmployeeEmail(final Employee employee) {
        return employee.email();
    }

    public static String returnEmployeeInfo(final Employee employee) {
        return employee.toString();
    }

    public static <T> Function<T, String> toFunction(
            final Function<Employee, T> function,
            final Employee employee
    ) {
        final T t = function.apply(employee);

        return element -> "This is " + element + " " + t;
    }

    public static <T> String toString(final Function<T, String> function, final T element) {
        return function.apply(element);
    }
}
