package Repositories.User;

import Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbclmpl implements UserRepository{

    private Connection connection;
    private Statement statement;

    private static final String SQL_SELECT_FROM_DRIVER = "select user_id, email, password, nickname, registration_date from users where user_id = ?";
    private static final String SQL_SELECT = "select user_id, email, password, nickname, registration_date from users";
    public UserRepositoryJdbclmpl (Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }



    @Override
    public List findById(long userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_FROM_DRIVER);
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = User.builder()
                        .UserId(resultSet.getLong("user_id"))
                        .UserEmail(resultSet.getString("email"))
                        .UserPassword(resultSet.getString("password"))
                        .UserNickname(resultSet.getString("nickname"))
                        .UserRegistration(resultSet.getDate("registration_date"))
//                        .UserEmailVerification(resultSet.getBoolean("email_verified"))
                        .build();

                result.add(user);
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List findAll() {
        try {
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            List<User> all = new ArrayList<>();

            while (resultSet.next()) {
                User user = User.builder()
                        .UserId(resultSet.getLong("user_id"))
                        .UserNickname("nickname")
                        .UserEmail("email")
                        .UserRegistration(resultSet.getDate("registration_date"))
                        .build();

                all.add(user);
            }

            return all;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}