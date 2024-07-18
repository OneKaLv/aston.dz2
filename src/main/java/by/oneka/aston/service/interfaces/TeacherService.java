package by.oneka.aston.service.interfaces;

import by.oneka.aston.dto.classes.TeacherDTO;

public interface TeacherService {
    TeacherDTO create(TeacherDTO teacherDTO);

    TeacherDTO read(Integer teacherID);

    boolean update(TeacherDTO teacherDTO);

    boolean delete(Integer teacherID);
}
