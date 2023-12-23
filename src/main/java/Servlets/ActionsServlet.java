package Servlets;

import Models.Task;
import Repositories.Account.AccountRepository;
import Repositories.Account.AccountRepositoryJdbclmpl;
import Repositories.File.FileRepository;
import Repositories.File.FileRepositoryJdbclmpl;
import Repositories.Task.TaskRepository;
import Repositories.Task.TaskRepositoryJdbclmpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

@WebServlet("/")
public class ActionsServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    private Connection connection;

    TaskRepository taskRepository;
    AccountRepository accountRepository;
    FileRepository fileRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            taskRepository = new TaskRepositoryJdbclmpl(connection);
            accountRepository = new AccountRepositoryJdbclmpl(connection);
            fileRepository = new FileRepositoryJdbclmpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/adminDelete":
                    adminDelete(request,response);
                    break;
                case "/deleteTask":
                    deleteTask(request, response);
                    break;
                case "/markFinished":
                    markFinished(request, response);
                    break;
                case "/loginAdmin":
                    login(request, response);
                    break;
                case "/detailsTask":
                    singleTask(request, response);
                    break;
                case "/viewFile":
                    viewFile(request, response);
                    break;
//                case "/logoutUser":
//                    logout(request,response);
//                    break;

                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/LoginPage.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void singleTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        long id = Long.parseLong(request.getParameter("task_id"));
        Task existingTask = taskRepository.findTaskById(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/singleTask.jsp");
        request.setAttribute("task", existingTask);
        dispatcher.forward(request,response);
//        response.sendRedirect("/details");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/login");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long userId = Long.parseLong(request.getParameter("user_id"));
        accountRepository.loginAdmin(userId);
        response.sendRedirect("/home");
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long taskId = Long.parseLong(request.getParameter("task_id"));
        taskRepository.deleteTaskById(taskId);
        response.sendRedirect("/taskByUser");
    }

    private void adminDelete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long taskId = Long.parseLong(request.getParameter("task_id"));
        taskRepository.deleteTaskById(taskId);
        response.sendRedirect("/allTasks");
    }

    private void markFinished(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long taskId = Long.parseLong(request.getParameter("task_id"));
        System.out.println(taskId);

        taskRepository.markTaskAsFinished(taskId);
        response.sendRedirect("/taskByUser");

    }

    private void viewFile(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long fileId = 0;
        long taskId = Long.parseLong(request.getParameter("task_id"));
        System.out.println(taskId);

        String sql = "SELECT f.file_id FROM files f JOIN tasks t ON f.file_id = t.file_id WHERE t.task_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,taskId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            fileId = Long.parseLong(resultSet.getString("file_id"));
            System.out.println(fileId);
        }

        // rest of your code for file handling
        byte[] fileContent = fileRepository.getFileContentByFileId(fileId);
        String fileName = fileRepository.getFileNameByFileId(fileId);
        String fileType = fileRepository.getFileTypeByFileId(fileId);

        response.setContentType(fileType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(fileContent);


    }

}

