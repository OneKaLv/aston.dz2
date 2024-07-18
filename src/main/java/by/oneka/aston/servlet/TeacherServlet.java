package by.oneka.aston.servlet;

import by.oneka.aston.dto.classes.TeacherDTO;
import by.oneka.aston.entity.Teacher;
import by.oneka.aston.mapper.classes.TeacherMapperImpl;
import by.oneka.aston.mapper.interfaces.Mapper;
import by.oneka.aston.repository.classes.TeacherRepositoryImpl;
import by.oneka.aston.repository.interfaces.TeacherRepository;
import by.oneka.aston.service.classes.TeacherServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/api/teachers/*")
public class TeacherServlet extends HttpServlet {

    TeacherRepository teacherRepository = TeacherRepositoryImpl.getTeacherRepository();
    Mapper<TeacherDTO, Teacher> teacherMapper = TeacherMapperImpl.getTeacherMapper();
    TeacherServiceImpl teacherService = new TeacherServiceImpl();

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
            Integer teacherID = Integer.parseInt(req.getPathInfo().split("/")[1]);
            TeacherDTO teacherDTO = teacherService.read(teacherID);
            resp.setStatus(HttpServletResponse.SC_OK);
            answer = objectMapper.writeValueAsString(teacherDTO);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("teacher not found")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                answer = "teacher not found";
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
            Optional<TeacherDTO> teacherResp = Optional.ofNullable(objectMapper.readValue(convertFromJson(req), TeacherDTO.class));
            TeacherDTO teacher = teacherResp.orElseThrow(IllegalArgumentException::new);
            answer = objectMapper.writeValueAsString(teacherRepository.create(teacherMapper.DTOToEntity(teacher)));
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
            Optional<TeacherDTO> teacherResp = Optional.ofNullable(objectMapper.readValue(json, TeacherDTO.class));
            TeacherDTO teacherDTO = teacherResp.orElseThrow(IllegalArgumentException::new);
            if (teacherService.update(teacherDTO))
                answer = objectMapper.writeValueAsString(teacherRepository.read(teacherDTO.getId()).orElseThrow(IllegalStateException::new));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Teacher not found: " + e.getLocalizedMessage())) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                answer = "Teacher not found";
            } else {
                answer = "Incorrect teacher data: " + e.getLocalizedMessage();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            answer = "Incorrect teacher data: " + e.getLocalizedMessage();
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
            Integer teacherId = Integer.parseInt(req.getPathInfo().split("/")[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            teacherService.delete(teacherId);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Teacher not found")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                answer = "Teacher not found";
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
