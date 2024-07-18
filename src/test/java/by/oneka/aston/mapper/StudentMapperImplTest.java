package by.oneka.aston.mapper;

import by.oneka.aston.dto.classes.StudentDTO;
import by.oneka.aston.entity.Student;
import by.oneka.aston.mapper.classes.StudentMapperImpl;
import by.oneka.aston.mapper.interfaces.Mapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@FieldDefaults(level = AccessLevel.PRIVATE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentMapperImplTest {
    Mapper<StudentDTO,Student> studentMapper;

    @BeforeAll
    void initMapper(){
        studentMapper = StudentMapperImpl.getStudentMapper();
    }

    @Test
    void mapDTO(){
        StudentDTO dto = new StudentDTO(
                1,
                "Vanya",
                "Kit",
                25,
                4,
                1
        );
        Student result = studentMapper.DTOToEntity(dto);
        Assertions.assertEquals(result.getId(), dto.getId());
        Assertions.assertEquals(result.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(result.getLastName(), dto.getLastName());
        Assertions.assertEquals(result.getAge(), dto.getAge());
        Assertions.assertEquals(result.getGrade(), dto.getGrade());
        Assertions.assertEquals(result.getTeacherID(), dto.getTeacherID());
    }

    @Test
    void mapEntity(){
        Student student = new Student(
                1,
                "Vanya",
                "Kit",
                25,
                4,
                1
        );
        StudentDTO result = studentMapper.entityToDTO(student);
        Assertions.assertEquals(result.getId(), student.getId());
        Assertions.assertEquals(result.getFirstName(), student.getFirstName());
        Assertions.assertEquals(result.getLastName(), student.getLastName());
        Assertions.assertEquals(result.getAge(), student.getAge());
        Assertions.assertEquals(result.getGrade(), student.getGrade());
        Assertions.assertEquals(result.getTeacherID(), student.getTeacherID());
    }
}
