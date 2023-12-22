package Servlets;

import Repositories.Task.TaskRepository;
import Repositories.Task.TaskRepositoryJdbclmpl;
import Repositories.User.UserRepositoryJdbclmpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet("/allTasks")
public class allTasksServlet extends HttpServlet {
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
            taskRepository = new TaskRepositoryJdbclmpl(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List allTasks;

        try {
            allTasks =  taskRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("allTasks", allTasks);
        request.getRequestDispatcher("/jsp/allTasks.jsp").forward(request, response);
    }
}

