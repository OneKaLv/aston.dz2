package by.oneka.aston.mapper.classes;

import by.oneka.aston.dto.classes.StudentDTO;
import by.oneka.aston.entity.Student;
import by.oneka.aston.mapper.interfaces.Mapper;

public class StudentMapperImpl implements Mapper<StudentDTO, Student> {

    private static Mapper<StudentDTO, Student> studentMapper;

    public static synchronized Mapper<StudentDTO, Student> getStudentMapper() {
        return studentMapper == null ? studentMapper = new StudentMapperImpl() : studentMapper;
    }

    @Override
    public StudentDTO entityToDTO(Student entity) {
        return new StudentDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getGrade(),
                entity.getTeacherID()
        );
    }

    @Override
    public Student DTOToEntity(StudentDTO dto) {
        return new Student(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge(),
                dto.getGrade(),
                dto.getTeacherID()
        );
    }
}
