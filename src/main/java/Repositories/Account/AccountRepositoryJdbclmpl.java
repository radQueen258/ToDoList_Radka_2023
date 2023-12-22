package Repositories.Account;

import Models.Role;
import Models.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountRepositoryJdbclmpl implements AccountRepository {

    private final Connection connection;

    //    private DataSource dataSource;
    private static final String SQL_INSERT ="insert into users(email, nickname, password, registration_date) values";

    public AccountRepositoryJdbclmpl(Connection connection) {
        super();
        this.connection = connection;
    }

//    public AccountRepositoryJdbclmpl(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }


//    @Override
//    public long getDefaultUserRoleID() throws SQLException {
//        String roleQuery = "select role_id from roles where role_name = 'USER' ";
//
//        try(PreparedStatement preparedStatement = connection.prepareStatement(roleQuery)) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getLong("role_id");
//                }
//            }
//        }
//
//        throw new SQLException("Default user role 'USER' not found.");
//    }

    @Override
    public void save(User user) throws SQLException {

        String sql = SQL_INSERT + "(?,?,?, CURRENT_DATE)";
//        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserEmail());
        preparedStatement.setString(2, user.getUserNickname());
        preparedStatement.setString(3, user.getUserPassword());
//        preparedStatement.setDate(4, user.getUserRegistration());
//        preparedStatement.setBoolean(5, user.getUserEmailVerification());

        preparedStatement.executeUpdate();

    }

    @Override
    public boolean login(String email, String password, User user, HttpServletRequest request) throws SQLException {

        String sql = "SELECT user_id, email, password FROM users WHERE email = ?";
//        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserEmail());
        ResultSet resultSet = preparedStatement.executeQuery();

        String userAcc = "";
        String passAcc = "";

        while (resultSet.next()) {
            userAcc = resultSet.getString("email");
            passAcc = resultSet.getString("password");

            System.out.println("Retrieved username: " + userAcc);
            System.out.println("Retrieved password: " + passAcc);
        }

        String admin = "";

        String sqlUserId = "SELECT role_name FROM roles WHERE role_id = ?";

        try (PreparedStatement preparedStatementRoles = connection.prepareStatement(sqlUserId)) {
            preparedStatementRoles.setLong(1, 1);
            ResultSet resultSetRole = preparedStatementRoles.executeQuery();

            if (resultSetRole.next()) {
                admin = resultSetRole.getString("role_name");
            }

            if ("ADMIN".equals(admin)) {
                return true;
            } else if (userAcc != null && passAcc != null && BCrypt.checkpw(password, passAcc)) {
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean loginAdmin(long userId) {

        try {
            String sqlLogin = "SELECT email, password FROM users WHERE user_id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlLogin);
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String userAcc = "";
            String passAcc = "";

            while (resultSet.next()) {
                userAcc = resultSet.getString("email");
                passAcc = resultSet.getString("password");

                System.out.println("Retrieved username: " + userAcc);
                System.out.println("Retrieved password: " + passAcc);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean findUUID(UUID uuid) throws SQLException {
        String sql = "select count(*) from users_uuid where UUID = ?";
//        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UUID addUUID(String email, User user) throws SQLException {
        String sqlUser = "select user_id from users where email = ?";
        String insertSqlUuid = "insert into users_uuid(user_id, uuid) values (?,?)";
//        Connection connection = dataSource.getConnection();

        UUID uuid = UUID.randomUUID();

        PreparedStatement preparedStatementUser = connection.prepareStatement(sqlUser);
        PreparedStatement preparedStatementUuid = connection.prepareStatement(insertSqlUuid);

        preparedStatementUser.setString(1, user.getUserEmail());
        int user_id = 0;
        ResultSet resultSetUser = preparedStatementUser.executeQuery();

        if (resultSetUser.next()) {
            user_id = resultSetUser.getInt("user_id");
        } else {
            throw new SQLException("User not found");
        }

        preparedStatementUuid.setInt(1, user_id);
        preparedStatementUuid.setObject(2, uuid);
        preparedStatementUuid.executeUpdate();

        return uuid;
    }
}
