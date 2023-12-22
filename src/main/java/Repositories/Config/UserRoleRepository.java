//package Repositories.Config;
//
//import Models.Role;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserRoleRepository {
//
//    private Connection connection;
//
//    public UserRoleRepository() {
//        this.connection = connection;
//    }
//
//
////    public List<Role> getUserRole(long userId) throws SQLException {
////        List<Role> userRoles = new ArrayList<>();
////        String sql = "SELECT r.role_name " +
////                "FROM roles r " +
////                "JOIN user_role ur ON r.role_id = ur.role_id " +
////                "WHERE ur.user_id = ?";
////
////        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
////            preparedStatement.setLong(1, userId);
////            ResultSet resultSet = preparedStatement.executeQuery();
////
////            while (resultSet.next()) {
//////                Long roleId = resultSet.getLong("role_id");
//////                String roleName = resultSet.getString("role_name");
////
////                Role role = Role.builder()
////                        .roleId(resultSet.getLong("role_id"))
////                        .roleName(resultSet.getString("role_name"))
////                        .roleDescription(resultSet.getString("description"))
////                        .build();
////
////                userRoles.add(role);
////            }
////
////        }
////        return  userRoles;
////    }
//}
