package Servlets;

import Models.Filees;
import Models.Task;
import Repositories.Account.AccountRepositoryJdbclmpl;
import Repositories.File.FileRepository;
import Repositories.File.FileRepositoryJdbclmpl;
import Repositories.Task.TaskRepository;
import Repositories.Task.TaskRepositoryJdbclmpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@WebServlet("/task")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize =  1024 * 1024 * 10, //10MB
        maxRequestSize = 1024 * 1024 * 50 ) //50MB
public class TaskServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    private Connection connection;

    private FileRepository fileRepository;
    private TaskRepository taskRepository;

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
            fileRepository = new FileRepositoryJdbclmpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/task").forward(request,response);


    }

    public long getUserIDFromCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("userSessionId") != null) {
            return (long) session.getAttribute("userSessionId");
        } else {
            // Handle the case where the user is not logged in or the user ID is not in the session
            return -1; // Or return any default value indicating no user ID
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String TaskName = request.getParameter("task_name");
        String TaskDescription = request.getParameter("description");
        String TaskDeadline1 = request.getParameter("deadline");
        long TaskUserId = getUserIDFromCurrentUser(request);


        System.out.println(TaskUserId);

        Part filePart = request.getPart("file");
        String fileName = "";
        byte[] fileContent = null;

        if (filePart != null && filePart.getSize() > 0) {

            fileName = filePart.getSubmittedFileName();
            String uploadPath = "/mnt/Radqueen/Projects Java/ToDoList_Radka_11-200/src/main/java/SavedFiles/" + fileName;
            System.out.println(uploadPath);
            String fileType = filePart.getContentType();

//            String filePath = uploadPath + File.separator + fileName;
//            filePart.write(filePath);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(uploadPath);
                InputStream inputStream = filePart.getInputStream();

                fileContent = new byte[inputStream.available()];
                inputStream.read(fileContent);
                fileOutputStream.write(fileContent);
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

//            fileContent = Files.readAllBytes(Paths.get(uploadPath));
            try {

                Filees files = Filees.builder()
                        .UserId(TaskUserId)
                        .FileName(fileName)
                        .FileType(fileType)
                        .FileContent(fileContent)
                        .build();

                fileRepository.saveFile(files);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Files.deleteIfExists(Paths.get(uploadPath));
        }

        Date taskDeadline = Date.valueOf(TaskDeadline1);
        Date currentDate = new Date(System.currentTimeMillis());

        if (taskDeadline.before(currentDate)) {
            response.sendRedirect("/task?error=invalidDeadline");
            return;
        } else {
            long fileId = 0;
            String sqlId = "select file_id from files where file_name = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlId);
                preparedStatement.setString(1,fileName);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    fileId = resultSet.getLong("file_id");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println(fileId);


            Task task = Task.builder()
                    .UserId(TaskUserId)
                    .FileId(fileId)
                    .TaskName(TaskName)
                    .TaskDescription(TaskDescription)
                    .TaskDeadline(Date.valueOf(TaskDeadline1))
                    .TaskStatus(null)
                    .build();


            try {
                taskRepository.saveTask(task);

                long taskId = 0;
                String sql = "select task_id from tasks where task_name = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,TaskName);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    taskId = resultSet.getLong("task_id");
                }

                System.out.println(taskId);
                request.setAttribute("taskId", taskId);
                request.setAttribute("fileId", fileId);
                response.sendRedirect("/taskByUser");
            } catch (SQLException e) {
                response.sendRedirect("/home");
                throw new RuntimeException(e);
            }
        }
    }
}
