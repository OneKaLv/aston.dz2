package by.oneka.aston.servlet;

import by.oneka.aston.dto.classes.StudentDTO;
import by.oneka.aston.entity.Student;
import by.oneka.aston.mapper.classes.StudentMapperImpl;
import by.oneka.aston.mapper.interfaces.Mapper;
import by.oneka.aston.repository.classes.StudentRepositoryImpl;
import by.oneka.aston.repository.interfaces.StudentRepository;
import by.oneka.aston.service.classes.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/api/students/*")
public class StudentServlet extends HttpServlet {

    StudentRepository studentRepository = StudentRepositoryImpl.getStudentRepository();
    Mapper<StudentDTO, Student> studentMapper = StudentMapperImpl.getStudentMapper();
    StudentServiceImpl studentService = new StudentServiceImpl();

    ObjectMapper objectMapper = new ObjectMapper();

    private static void setConnectionType(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private static String convertFromJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            sb.append(line);
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setConnectionType(resp);

        String answer = "";
        try {
            Integer studentID = Integer.parseInt(req.getPathInfo().split("/")[1]);
            StudentDTO studentDTO = studentService.read(studentID);
            resp.setStatus(HttpServletResponse.SC_OK);
            answer = objectMapper.writeValueAsString(studentDTO);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("student not found")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                answer = "student not found";
            } else {
                answer = "Bad request";
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            answer = "Bad request";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(answer);
        printWriter.flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setConnectionType(resp);
        String answer = "";
        try {
            Optional<StudentDTO> studentResp = Optional.ofNullable(objectMapper.readValue(convertFromJson(req), StudentDTO.class));
            StudentDTO student = studentResp.orElseThrow(IllegalArgumentException::new);
            answer = objectMapper.writeValueAsString(studentRepository.create(studentMapper.DTOToEntity(student)));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
            answer = "Wrong input data";
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(answer);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setConnectionType(resp);
        String json = convertFromJson(req);
        String answer = "";
        try {
            Optional<StudentDTO> studentResp = Optional.ofNullable(objectMapper.readValue(json, StudentDTO.class));
            StudentDTO studentDTO = studentResp.orElseThrow(IllegalArgumentException::new);
            if (studentService.update(studentDTO))
                answer = objectMapper.writeValueAsString(studentRepository.read(studentDTO.getId()).orElseThrow(IllegalStateException::new));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Student not found: " + e.getLocalizedMessage())) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                answer = "Student not found";
            } else {
                answer = "Incorrect student data: " + e.getLocalizedMessage();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            answer = "Incorrect student data: " + e.getLocalizedMessage();
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(answer);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setConnectionType(resp);
        String answer = "";
        try {
            Integer studentId = Integer.parseInt(req.getPathInfo().split("/")[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            studentService.delete(studentId);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Student not found")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                answer = "Student not found";
            } else {
                answer = "Bad request";
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            answer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(answer);
        printWriter.flush();
    }
}
