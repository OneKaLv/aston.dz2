package by.oneka.aston.service;

import by.oneka.aston.dto.classes.StudentDTO;
import by.oneka.aston.entity.Student;
import by.oneka.aston.repository.classes.StudentRepositoryImpl;
import by.oneka.aston.repository.interfaces.StudentRepository;
import by.oneka.aston.service.classes.StudentServiceImpl;
import by.oneka.aston.service.interfaces.StudentService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceImplTest {

    private static StudentService studentService;
    private static StudentRepository newStudentRepository;
    private static StudentRepositoryImpl oldStudentRepository;

    private static void setMock(StudentRepository mock) {
        try {
            Field studentRepository = StudentRepositoryImpl.class.getDeclaredField("studentRepository");
            studentRepository.setAccessible(true);
            oldStudentRepository = (StudentRepositoryImpl) studentRepository.get(studentRepository);
            studentRepository.set(studentRepository, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void replaceRepository() {
        newStudentRepository = Mockito.mock(StudentRepository.class);
        setMock(newStudentRepository);
        studentService = StudentServiceImpl.getStudentService();
    }

    @AfterAll
    static void revertRepository() throws Exception {
        Field studentRepository = StudentRepositoryImpl.class.getDeclaredField("studentRepository");
        studentRepository.setAccessible(true);
        studentRepository.set(studentRepository, oldStudentRepository);
    }

    @BeforeEach
    public void mockReset() {
        Mockito.reset(newStudentRepository);
    }


    @Test
    public void create() {
        Integer id = 1;

        StudentDTO studentDTO = new StudentDTO(
                null,
                "Vanya",
                "Kit",
                22,
                1,
                1);
        Student student = new Student(
                id,
                "Vanya",
                "Kit",
                22,
                1,
                1
        );

        Mockito.doReturn(student).when(newStudentRepository).create(Mockito.any(Student.class));

        StudentDTO result = studentService.create(studentDTO);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(result.getFirstName(), student.getFirstName());
    }
}
