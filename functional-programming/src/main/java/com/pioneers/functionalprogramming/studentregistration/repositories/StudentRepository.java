package com.pioneers.functionalprogramming.studentregistration.repositories;

import com.pioneers.functionalprogramming.studentregistration.models.entities.Student;

import java.util.Optional;

public interface StudentRepository {

    void save(Student student);

    Optional<Student> findByEmail(String email);
}
