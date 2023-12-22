package Servlets;

import Models.User;
import Repositories.Account.AccountRepository;
import Repositories.Account.AccountRepositoryJdbclmpl;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";

    private AccountRepository accountRepository;
//    private DataSource dataSource;
//
//    public SignUpServlet(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    private Connection connection;


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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        accountRepository = new AccountRepositoryJdbclmpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            response.sendRedirect("/home");
            return;
        }

        Cookie[] cookies = request.getCookies();
        UUID userUUID = null;

        if (cookies != null) {
            for (Cookie cookie :  cookies) {
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

        request.getRequestDispatcher("/jsp/SignUpPage.jsp").forward(request, response);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountUserEmail = request.getParameter("email");
        String accountUserPass = request.getParameter("password");
        String accountUserNickname = request.getParameter("nickname");
        String hashedPassword = hashPassword(accountUserPass);


        User user = User.builder()
                .UserEmail(accountUserEmail)
                .UserPassword(hashedPassword)
                .UserNickname(accountUserNickname)
                .build();

        long userId = -1;

        try {
            accountRepository.save(user);

            String sqlUserId = "SELECT user_id FROM users WHERE email = ?";
//            Connection connection = dataSource.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUserId)) {
                preparedStatement.setString(1, accountUserEmail);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    userId = resultSet.getLong("user_id"); // Retrieve the user_id
                }


                String sql = "insert into user_roles (user_id, role_id) values (?, ?)";
                try(PreparedStatement preparedStatement1 = connection.prepareStatement(sql)) {
                    preparedStatement1.setLong(1,userId);
                    preparedStatement1.setLong(2,2);
                    preparedStatement1.executeUpdate();
                }


                // redirect to take you to login page after registering
                response.sendRedirect("/save");
            } catch (SQLException e) {
                response.sendRedirect("/signUp");
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
