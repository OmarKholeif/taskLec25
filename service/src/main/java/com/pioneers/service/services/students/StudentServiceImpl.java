package com.pioneers.service.services.students;

import com.pioneers.service.errors.exceptions.StudentException;
import com.pioneers.service.models.dtos.requests.StudentUpdate;
import com.pioneers.service.models.dtos.responses.StudentResponse;
import com.pioneers.service.models.entities.Student;
import com.pioneers.service.repositories.StudentRepository;
import com.pioneers.service.utils.mappers.StudentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.pioneers.service.utils.CredentialsHelper.hashPassword;
import static com.pioneers.service.utils.NameBuilder.buildFullName;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponse> findAllSortedByAge() throws StudentException {
        final String methodName = "findAllSortedByAge";
        final Collection<Student> sortedStudentsByAge = studentRepository.findAllSortedByAge();

        log.debug("{}, Successfully fetched all students sorted by age", methodName);

        if (sortedStudentsByAge.isEmpty()) {
            log.error("{}, No students found in the DB!", methodName);
            throw new StudentException("No students found in the DB");
        }

        final List<StudentResponse> studentResponses = sortedStudentsByAge.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();

        log.debug("{}, Successfully mapped the sorted students by age to studentResponses with size: [{}]",
                methodName, sortedStudentsByAge.size());

        return studentResponses;
    }

    @Override
    public StudentResponse findById(final UUID id) throws StudentException {
        final Optional<Student> optionalFoundStudent = studentRepository.findById(id);

        if (optionalFoundStudent.isEmpty()) {
            throw new StudentException("Student doesn't exist in the DB");
        }

        return StudentMapper.toStudentResponse(optionalFoundStudent.get());
    }

    @Override
    public Collection<StudentResponse> findAllSucceeded() throws StudentException {
        final Collection<Student> succeededStudents = studentRepository.findAllSucceeded();

        if (succeededStudents.isEmpty()) {
            throw new StudentException("No success students found in the DB");
        }

        return succeededStudents.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();

    }

    @Override
    public void update(final UUID id, final StudentUpdate studentUpdateRequest) throws StudentException {

        studentUpdateRequest.validate();

        final Optional<Student> optionalFoundStudent = studentRepository.findById(id);

        if (optionalFoundStudent.isEmpty()) {
            throw new StudentException("Student doesn't exist in the DB");
        }

        final String updatedFullName = buildFullName(studentUpdateRequest.firstName(), studentUpdateRequest.secondName());
        final String updatedHashedPassword = hashPassword(studentUpdateRequest.password());

        final Student foundStudent = optionalFoundStudent.get();
        foundStudent.setFullName(updatedFullName);
        foundStudent.setEmail(studentUpdateRequest.email());
        foundStudent.setAge(studentUpdateRequest.age());
        foundStudent.setPassword(updatedHashedPassword);
        foundStudent.setScore(studentUpdateRequest.score());
    }

    @Override
    public void deleteById(final UUID id) throws StudentException {
        final Optional<Student> optionalFoundStudent = studentRepository.findById(id);

        if (optionalFoundStudent.isEmpty()) {
            throw new StudentException("No success students found in the DB");
        }

        studentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        studentRepository.deleteAll();
    }
}
