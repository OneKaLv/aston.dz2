package by.oneka.aston.mapper;

import by.oneka.aston.dto.classes.TeacherDTO;
import by.oneka.aston.entity.Teacher;
import by.oneka.aston.mapper.classes.TeacherMapperImpl;
import by.oneka.aston.mapper.interfaces.Mapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@FieldDefaults(level = AccessLevel.PRIVATE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherMapperImplTest {
    Mapper<TeacherDTO,Teacher> teacherMapper;

    @BeforeAll
    void initMapper(){
        teacherMapper = TeacherMapperImpl.getTeacherMapper();
    }

    @Test
    void mapDTO(){
        TeacherDTO dto = new TeacherDTO(
                1,
                "Vanya",
                "Kit",
                25,
                4,
                "Math"
        );
        Teacher result = teacherMapper.DTOToEntity(dto);
        Assertions.assertEquals(result.getId(), dto.getId());
        Assertions.assertEquals(result.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(result.getLastName(), dto.getLastName());
        Assertions.assertEquals(result.getAge(), dto.getAge());
        Assertions.assertEquals(result.getExperience(), dto.getExperience());
        Assertions.assertEquals(result.getSubject(), dto.getSubject());
    }

    @Test
    void mapEntity(){
        Teacher teacher = new Teacher(
                1,
                "Vanya",
                "Kit",
                25,
                4,
                "Math"
        );
        TeacherDTO result = teacherMapper.entityToDTO(teacher);
        Assertions.assertEquals(result.getId(), teacher.getId());
        Assertions.assertEquals(result.getFirstName(), teacher.getFirstName());
        Assertions.assertEquals(result.getLastName(), teacher.getLastName());
        Assertions.assertEquals(result.getAge(), teacher.getAge());
        Assertions.assertEquals(result.getExperience(), teacher.getExperience());
        Assertions.assertEquals(result.getSubject(), teacher.getSubject());
    }
}
