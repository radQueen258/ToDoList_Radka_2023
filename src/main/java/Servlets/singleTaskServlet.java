package Servlets;

import Models.Filees;
import Models.Task;
import Repositories.File.FileRepository;
import Repositories.File.FileRepositoryJdbclmpl;
import Repositories.Task.TaskRepository;
import Repositories.Task.TaskRepositoryJdbclmpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet("/details")
public class singleTaskServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    private TaskRepository taskRepository;
    private FileRepository fileRepository;


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
            fileRepository = new FileRepositoryJdbclmpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        long fileId = Long.parseLong(request.getParameter("file_id"));
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("userSessionId");
//        long taskId = (long) request.getAttribute("taskId");
//        System.out.println(taskId);


        Task task = taskRepository.findTaskById(73);
//        Filees filees = fileRepository.fileByDetails(fileId);
//        List userTasks;
//        try {
//            userTasks = taskRepository.findByUser(userId);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        request.setAttribute("details", task);
//        request.setAttribute("details", userTasks);
//        request.setAttribute("fileDetails", filees);
        request.getRequestDispatcher("/jsp/singleTask.jsp").forward(request, response);
    }
}
