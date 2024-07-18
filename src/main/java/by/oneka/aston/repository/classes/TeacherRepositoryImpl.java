package by.oneka.aston.repository.classes;

import by.oneka.aston.datasourse.ConnectionManager;
import by.oneka.aston.entity.Teacher;
import by.oneka.aston.repository.interfaces.TeacherRepository;

import java.sql.*;
import java.util.Optional;

public class TeacherRepositoryImpl implements TeacherRepository {
    private static final String CREATE = "insert into teachers (firstName, lastName, age, experience, subject) values(?, ?, ?, ?, ?)";
    private static final String READ = "select * from teachers where id = ?";
    private static final String UPDATE = "update teachers set firstName = ?, lastName = ?, age = ?, experience = ?, subject = ? where id = ?";
    private static final String DELETE = "delete from teachers where id = ?";

    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManager();

    private static TeacherRepository teacherRepository;


    public static synchronized TeacherRepository getTeacherRepository() {
        return teacherRepository == null ? teacherRepository = new TeacherRepositoryImpl() : teacherRepository;
    }

    @Override
    public Teacher create(Teacher teacher) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setInt(3, teacher.getAge());
            preparedStatement.setInt(4, teacher.getExperience());
            preparedStatement.setString(5, teacher.getSubject());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                teacher = new Teacher(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("subject")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Teacher didn`t created: " + e.getLocalizedMessage());
        }

        return teacher;
    }

    @Override
    public Optional<Teacher> read(Integer id) {
        Teacher teacher = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {
            preparedStatement.setInt(1, id);
            ResultSet e = preparedStatement.executeQuery();
            if (e.next())
                teacher = createTeacher(e);
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public boolean update(Teacher teacher) {
        boolean isUpdated;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setInt(3, teacher.getAge());
            preparedStatement.setInt(4, teacher.getExperience());
            preparedStatement.setString(5, teacher.getSubject());
            preparedStatement.setInt(6, teacher.getId());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Teacher is not updated:" + e.getLocalizedMessage());
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
            throw new RuntimeException("Teacher not deleted: " + e.getLocalizedMessage());
        }
        return isDelete;
    }

    private Teacher createTeacher(ResultSet resultSet) {
        Teacher teacher;
        try {
            teacher = new Teacher(
                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age"),
                    resultSet.getInt("experience"),
                    resultSet.getString("subject")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }
}
