package com.pioneers.functionalprogramming.studentregistration.repositories;

import com.pioneers.functionalprogramming.studentregistration.models.entities.Student;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<UUID, Student> students = new ConcurrentHashMap<>();

    @Override
    public void save(final Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public Optional<Student> findByEmail(final String email) {
        return students.values()
                .stream()
                .filter(student -> student.getEmail().equals(email))
                .findFirst();
    }

}
