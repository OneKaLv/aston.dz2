package by.oneka.aston.service.classes;

import by.oneka.aston.dto.classes.TeacherDTO;
import by.oneka.aston.entity.Teacher;
import by.oneka.aston.mapper.classes.TeacherMapperImpl;
import by.oneka.aston.mapper.interfaces.Mapper;
import by.oneka.aston.repository.classes.TeacherRepositoryImpl;
import by.oneka.aston.repository.interfaces.TeacherRepository;
import by.oneka.aston.service.interfaces.TeacherService;

public class TeacherServiceImpl implements TeacherService {
    TeacherRepository teacherRepository = TeacherRepositoryImpl.getTeacherRepository();
    Mapper<TeacherDTO, Teacher> teacherMapper = TeacherMapperImpl.getTeacherMapper();

    @Override
    public TeacherDTO create(TeacherDTO teacherDTO) {
        return teacherMapper.entityToDTO(teacherRepository.create(teacherMapper.DTOToEntity(teacherDTO)));
    }

    @Override
    public TeacherDTO read(Integer teacherID) {
        return teacherMapper.entityToDTO(teacherRepository.read(teacherID).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public boolean update(TeacherDTO teacherDTO) {
        return teacherRepository.update(teacherMapper.DTOToEntity(teacherDTO));
    }

    @Override
    public boolean delete(Integer teacherID) {
        checkTeacher(teacherID);
        return teacherRepository.delete(teacherID);
    }

    private void checkTeacher(Integer teacherId) throws RuntimeException {
        if (teacherRepository.read(teacherId).isEmpty()) {
            throw new RuntimeException("Teacher not found");
        }
    }
}
