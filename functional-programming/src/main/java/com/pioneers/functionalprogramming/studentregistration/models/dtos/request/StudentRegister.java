package com.pioneers.functionalprogramming.studentregistration.models.dtos.request;

public record StudentRegister(String firstName, String lastName, int age, String email, String password) {

    public String buildFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "StudentRegister{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='********'" +
                '}';
    }
}
