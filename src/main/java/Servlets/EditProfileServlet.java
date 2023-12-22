package Servlets;

import Models.User;
import Repositories.Account.AccountRepository;
import Repositories.Account.AccountRepositoryJdbclmpl;
import Repositories.User.UserRepository;
import Repositories.User.UserRepositoryJdbclmpl;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/edit")
public class EditProfileServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            userRepository = new UserRepositoryJdbclmpl(connection,statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        accountRepository = new AccountRepositoryJdbclmpl(connection);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("userSessionId");

        List result;

        try {
            result = userRepository.findById(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("edit", result);
        request.getRequestDispatcher("/jsp/editProfile.jsp").forward(request, response);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("userSessionId");

        String changedEmail = request.getParameter("email");
        String changedPass = request.getParameter("newPass");
        String changedNick = request.getParameter("nickname");
        String hashedChangedPass = hashPassword(changedPass);

        String accSQL = "select email, password, nickname from users where user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(accSQL);
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String emailUser = "";
            String passUser = "";
            String nickUser = "";

            while (resultSet.next()) {
                emailUser = resultSet.getString("email");
                passUser = resultSet.getString("password");
                nickUser = resultSet.getString("nickname");
            }
            String updateQuery = "";

            if (!changedEmail.equals(emailUser) && BCrypt.checkpw(changedPass,passUser) && changedNick.equals(nickUser)) {
                updateQuery = "update users set email = ? where user_id = ?";
            } else if (!BCrypt.checkpw(changedPass, passUser) && changedEmail.equals(emailUser) && changedNick.equals(nickUser)) {
                updateQuery = "update users set password = ? where user_id = ?";
            } else if (!changedNick.equals(nickUser) && BCrypt.checkpw(changedPass,passUser) && changedEmail.equals(emailUser)) {
                updateQuery = "update users set nickname = ? where user_id = ?";
            } else if (!changedEmail.equals(emailUser) && !BCrypt.checkpw(changedPass, passUser) && changedNick.equals(nickUser)) {
                updateQuery = "update users set email = ?, password = ? where user_id = ?";
            } else if (!changedEmail.equals(emailUser) && !changedNick.equals(nickUser) && BCrypt.checkpw(changedPass,passUser)) {
                updateQuery = "update users set email = ?, nickname = ? where user_id = ?";
            } else if (!BCrypt.checkpw(changedPass, passUser) && !changedNick.equals(nickUser) && changedEmail.equals(emailUser)) {
                updateQuery = "update users set password = ?, nickname = ? where user_id = ?";
            } else {
                updateQuery = "update users set email = ?, password = ?, nickname = ? where user_id = ?";
            }

                if (updateQuery != null) {
                    switch (updateQuery) {
                        case "update users set email = ? where user_id = ?":
                            try (PreparedStatement preparedStatementEmail = connection.prepareStatement(updateQuery)) {
                                preparedStatementEmail.setString(1, changedEmail);
                                preparedStatementEmail.setLong(2, userId);
                                preparedStatementEmail.executeUpdate();

                            }
                            break;
                        case "update users set password = ? where user_id = ?":
                            try (PreparedStatement preparedStatementPass = connection.prepareStatement(updateQuery)) {
                                preparedStatementPass.setString(1, hashedChangedPass);
                                preparedStatementPass.setLong(2, userId);
                                preparedStatementPass.executeUpdate();

                            }
                            break;
                        case "update users set nickname = ? where user_id = ?":
                            try (PreparedStatement preparedStatementNick = connection.prepareStatement(updateQuery)) {
                                preparedStatementNick.setString(1, changedNick);
                                preparedStatementNick.setLong(2, userId);
                                preparedStatementNick.executeUpdate();

                            }
                            break;
                        case "update users set email = ?, password = ? where user_id = ?":
                            try (PreparedStatement preparedStatement1 = connection.prepareStatement(updateQuery)) {
                                preparedStatement1.setString(1,changedEmail);
                                preparedStatement1.setString(2,hashedChangedPass);
                                preparedStatement1.setLong(3, userId);

                            }
                            break;
                        case "update users set email = ?, nickname = ? where user_id = ?":
                            try (PreparedStatement preparedStatement2 = connection.prepareStatement(updateQuery)){
                                preparedStatement2.setString(1,changedEmail);
                                preparedStatement2.setString(2,changedNick);
                                preparedStatement2.setLong(3,userId);

                            }
                            break;
                        case "update users set password = ?, nickname = ? where user_id = ?":
                            try (PreparedStatement preparedStatement3 = connection.prepareStatement(updateQuery)){
                                preparedStatement3.setString(1,hashedChangedPass);
                                preparedStatement3.setString(2, changedNick);
                                preparedStatement3.setLong(3,userId);

                            }
                            break;
                        case "update users set email = ?, password = ?, nickname = ? where user_id = ?":
                            try (PreparedStatement preparedStatement4 = connection.prepareStatement(updateQuery)) {
                                preparedStatement4.setString(1, changedEmail);
                                preparedStatement4.setString(2,hashedChangedPass);
                                preparedStatement4.setString(3,changedNick);
                                preparedStatement4.setLong(4, userId);

                            }
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + updateQuery);
                    }
                }
                response.sendRedirect("/profile");
            }  catch (SQLException e) {
            response.sendRedirect("/edit?error=1");
            throw new RuntimeException(e);

        }



    }
}
