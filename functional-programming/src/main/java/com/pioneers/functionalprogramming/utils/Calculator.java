package com.pioneers.functionalprogramming.utils;


public class Calculator {

    public static int add(final int num1, final int num2) {
        return num1 + num2;
    }

    public static float add(final float num1, final float num2) {
        return num1 + num2;
    }

    public static int subtract(final int num1, final int num2) {
        return num1 - num2;
    }

    public static float subtract(final float num1, final float num2) {
        return num1 - num2;
    }

    public static int multiply(final int num1, final int num2) {
        return num1 * num2;
    }

    public static float multiply(final float num1, final float num2) {
        return num1 * num2;
    }




    public static Student changeName(final Student student) {
        final Student newStudent = new Student(student);

        newStudent.setName("Mr. " + student.getName());
        return newStudent;
    }

    public static Student newStudent(final Student student) {
        return new Student("Zizo");
    }

    public static Student changeName(final StudentService<Student> function, final Student student) {
        return function._2delo(student);
    }

    public static void main(String[] args) {
        Student student = new Student("Mostafa");
        final Student updatedStudent = changeName(Calculator::changeName, student);

        System.out.println("name = " + updatedStudent.getName());
    }

    public static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public Student(Student student) {
            this.name = student.getName();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
