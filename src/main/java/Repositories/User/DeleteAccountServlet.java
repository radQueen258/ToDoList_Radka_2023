//package Repositories.User;
//
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//@WebServlet("/delete")
//public class DeleteAccountServlet extends HttpServlet {
//
//    private static final String DB_USER = "postgres";
//    private static final String DB_PASSWORD = "postgres";
//    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";
//
//    private Connection connection;
//
//    @Override
//    public void init() throws ServletException {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long userId = -1;
//        HttpSession session = request.getSession();
//        session.setAttribute("user_id",userId);
//
//        String sql = "DELETE FROM users WHERE user_id = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1,userId);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().println("Account deleted successfully");
//            } else {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().println("Account not found or deletion failed");
//            }
//
//            preparedStatement.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Error deleting account");
//        }
//
//
//    }
//}
