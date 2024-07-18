package by.oneka.aston.repository.interfaces;

import by.oneka.aston.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends Repository<Teacher, Integer>{
    Teacher create(Teacher student);

    Optional<Teacher> read(Integer id);

    boolean update(Teacher student);

    boolean delete(Integer id);
}
