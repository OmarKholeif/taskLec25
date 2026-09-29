package com.pioneers.functionalprogramming.studentregistration.models.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class Student {
    private UUID id;
    private String name;
    private int age;
    private String email;
    private String password;

    public Student( String name, int age, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='********'" +
                '}';
    }
}
