package by.oneka.aston.repository;

import by.oneka.aston.entity.Student;
import by.oneka.aston.repository.classes.StudentRepositoryImpl;
import by.oneka.aston.repository.interfaces.StudentRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRepositoryImplTest {
    public static Integer testID;
    StudentRepository studentRepository = StudentRepositoryImpl.getStudentRepository();

    @Test
    @Order(1)
    public void create() {
        Student student = new Student(
                null,
                "Vanya",
                "Kit",
                22,
                4,
                1);
        student = studentRepository.create(student);
        Optional<Student> result = studentRepository.read(student.getId());
        result.ifPresent(s -> testID = s.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Vanya", result.get().getFirstName());
        Assertions.assertEquals("Kit", result.get().getLastName());
        Assertions.assertEquals(22, result.get().getAge());
        Assertions.assertEquals(4, result.get().getGrade());
        Assertions.assertEquals(1, result.get().getTeacherID());
    }

    @Test
    @Order(2)
    public void updateAndRead() {
        Student student = studentRepository.read(testID).get();

        student.setFirstName("Kirill");
        studentRepository.update(student);

        Student result = studentRepository.read(testID).get();

        Assertions.assertEquals("Kirill", result.getFirstName());
        Assertions.assertEquals("Kit", result.getLastName());
        Assertions.assertEquals(22, result.getAge());
        Assertions.assertEquals(4, result.getGrade());
        Assertions.assertEquals(1, result.getTeacherID());
    }

    @Test
    @Order(3)
    public void delete() {
        boolean result = studentRepository.delete(testID);
        Assertions.assertTrue(result);
    }
}
