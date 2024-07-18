package by.oneka.aston.service.interfaces;

import by.oneka.aston.dto.classes.StudentDTO;

public interface StudentService {
    StudentDTO create(StudentDTO studentDTO);

    StudentDTO read(Integer studentID);

    boolean update(StudentDTO studentDTO);

    boolean delete(Integer studentDTO);
}
