package Servlets;

import Models.User;
import Repositories.Account.AccountRepository;
import Repositories.Account.AccountRepositoryJdbclmpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    AccountRepository accountRepository;
    //    private DataSource dataSource;
    private  Connection connection;

    @Override
    public void init(ServletConfig config) throws ServletException {
//        accountRepository = (AccountRepository) config.getServletContext().getAttribute("accountRep");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            accountRepository = new AccountRepositoryJdbclmpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession(false);


        if (session != null) {
            String userId = (String) session.getAttribute("userSessionId");

            if (userId != null) {
                response.sendRedirect("/home");
                return;
            }
        }

        Cookie[] cookies = request.getCookies();
        UUID userUUID = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_id".equals(cookie.getName())) {
                    userUUID = UUID.fromString(cookie.getValue());
                    break;
                }
            }
        }

        if (userUUID != null) {
            try {
                if (accountRepository.findUUID(userUUID)) {
                    response.sendRedirect("/home");
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        request.getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountUserEmail = request.getParameter("email");
        String accountUserPassword = request.getParameter("password");

        User user = User.builder()
                .UserEmail(accountUserEmail)
                .UserPassword(accountUserPassword)
                .build();

        try {

            if (accountUserEmail.equals("admin@gmail.com")) {

                Cookie sessionCookie = new Cookie("user_id", accountRepository.addUUID(accountUserEmail, user).toString());
                sessionCookie.setMaxAge(60 * 60);
                response.addCookie(sessionCookie);
                response.sendRedirect("/allTasks");
            } else{
                if (accountRepository.login(accountUserEmail, accountUserPassword, user, request)) {
                    long accountUserId = -1;
                    String sqlUserId = "SELECT user_id FROM users WHERE email = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUserId)) {
                        preparedStatement.setString(1, accountUserEmail);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            accountUserId = resultSet.getLong("user_id");
                        }
                    }

                    HttpSession session = request.getSession(); //there was a 'true'
                    session.setAttribute("userSessionId", accountUserId);

                    Cookie sessionCookie = new Cookie("user_id", accountRepository.addUUID(accountUserEmail, user).toString());
                    sessionCookie.setMaxAge(60 * 60);
                    response.addCookie(sessionCookie);

                    response.sendRedirect("/home");
                } else {
                    System.out.println("Login failed. Redirecting to /login?error=1");
                    response.sendRedirect("/login?error=1");
                }
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
