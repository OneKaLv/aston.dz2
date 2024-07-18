package by.oneka.aston.mapper.classes;

import by.oneka.aston.dto.classes.TeacherDTO;
import by.oneka.aston.entity.Teacher;
import by.oneka.aston.mapper.interfaces.Mapper;

public class TeacherMapperImpl implements Mapper<TeacherDTO, Teacher> {
    private static Mapper<TeacherDTO, Teacher> teacherMapper;
    public static synchronized Mapper<TeacherDTO, Teacher> getTeacherMapper() {
        return teacherMapper == null ? teacherMapper = new TeacherMapperImpl() : teacherMapper;
    }

    @Override
    public TeacherDTO entityToDTO(Teacher entity) {
        return new TeacherDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getExperience(),
                entity.getSubject()
        );
    }

    @Override
    public Teacher DTOToEntity(TeacherDTO dto) {
        return new Teacher(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge(),
                dto.getExperience(),
                dto.getSubject()
        );
    }
}
