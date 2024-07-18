package by.oneka.aston.service.classes;

import by.oneka.aston.dto.classes.StudentDTO;
import by.oneka.aston.entity.Student;
import by.oneka.aston.mapper.classes.StudentMapperImpl;
import by.oneka.aston.mapper.interfaces.Mapper;
import by.oneka.aston.repository.classes.StudentRepositoryImpl;
import by.oneka.aston.repository.interfaces.StudentRepository;
import by.oneka.aston.service.interfaces.StudentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository = StudentRepositoryImpl.getStudentRepository();
    Mapper<StudentDTO, Student> studentMapper = StudentMapperImpl.getStudentMapper();

    @NonFinal
    private static StudentService studentService;

    public static synchronized StudentService getStudentService() {
        return studentService == null ? studentService = new StudentServiceImpl() : studentService;
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        return studentMapper.entityToDTO(studentRepository.create(studentMapper.DTOToEntity(studentDTO)));
    }

    @Override
    public StudentDTO read(Integer studentID) {
        return studentMapper.entityToDTO(studentRepository.read(studentID).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public boolean update(StudentDTO studentDTO) {
        return studentRepository.update(studentMapper.DTOToEntity(studentDTO));
    }

    @Override
    public boolean delete(Integer studentID) {
        checkStudent(studentID);
        return studentRepository.delete(studentID);
    }

    private void checkStudent(Integer studentId) throws RuntimeException {
        if (studentRepository.read(studentId).isEmpty()) {
            throw new RuntimeException("Student not found");
        }
    }
}
