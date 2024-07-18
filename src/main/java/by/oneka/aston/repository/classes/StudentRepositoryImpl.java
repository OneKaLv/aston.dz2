package by.oneka.aston.repository.classes;

import by.oneka.aston.datasourse.ConnectionManager;
import by.oneka.aston.entity.Student;
import by.oneka.aston.repository.interfaces.StudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryImpl implements StudentRepository {

    private static final String CREATE = "insert into students (firstName, lastName, age, grade, teacherId) values(?, ?, ?, ?,?)";
    private static final String READ = "select * from students where id = ?";
    private static final String UPDATE = "update students set firstName = ?, lastName = ?, age = ?, grade = ?, teacherId = ? where id = ?";
    private static final String DELETE = "delete from students where id = ?";
    private static final String FIND_ALL_STUDENTS = "select * from students where teacherId = ?";
    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManager();

    private static StudentRepository studentRepository;


    public static synchronized StudentRepository getStudentRepository() {
        return studentRepository == null? studentRepository = new StudentRepositoryImpl() : studentRepository;
    }

    @Override
    public Student create(Student student) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setInt(4, student.getGrade());
            preparedStatement.setInt(5, student.getTeacherID());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getInt("grade"),
                        resultSet.getInt("teacherId")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Student didn`t created: " + e.getLocalizedMessage());
        }

        return student;
    }

    @Override
    public Optional<Student> read(Integer id) {
        Student student = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {
            preparedStatement.setInt(1, id);
            ResultSet e = preparedStatement.executeQuery();
            if (e.next())
                student = createStudent(e);
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(student);
    }

    @Override
    public boolean update(Student student) {
        boolean isUpdated;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setInt(4, student.getGrade());
            preparedStatement.setInt(5, student.getTeacherID());
            preparedStatement.setInt(6, student.getId());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Student is not updated:" + e.getLocalizedMessage());
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Integer id) {
        boolean isDelete;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            isDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Student not deleted: " + e.getLocalizedMessage());
        }
        return isDelete;
    }

    @Override
    public Optional<List<Student>> findAllStudentsByTeacherId(Integer id) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_STUDENTS)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                students.add(createStudent(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Student not found");
        }
        return Optional.of(students);
    }

    private Student createStudent(ResultSet resultSet) {
        Student student;
        try {
            student = new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age"),
                    resultSet.getInt("grade"),
                    (Integer) resultSet.getObject("teacherID")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

}
