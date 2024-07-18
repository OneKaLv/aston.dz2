package by.oneka.aston.repository.interfaces;

import by.oneka.aston.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends Repository<Student, Integer> {
    Student create(Student student);

    Optional<Student> read(Integer id);

    boolean update(Student student);

    boolean delete(Integer id);

    Optional<List<Student>> findAllStudentsByTeacherId(Integer id);
}
