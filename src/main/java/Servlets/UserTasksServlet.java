package Servlets;

import Models.Task;
import Repositories.Account.AccountRepositoryJdbclmpl;
import Repositories.Task.TaskRepository;
import Repositories.Task.TaskRepositoryJdbclmpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet("/taskByUser")
public class UserTasksServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    private TaskRepository taskRepository;


    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            taskRepository = new TaskRepositoryJdbclmpl(connection,statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("userSessionId");

        List userTasks;
        try {
            userTasks = taskRepository.findByUser(userId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("taskJsp", userTasks);
        request.getRequestDispatcher("/jsp/TasksListPage.jsp").forward(request, response);
        request.getRequestDispatcher("/jsp/singleTask.jsp").forward(request, response);
    }
}
