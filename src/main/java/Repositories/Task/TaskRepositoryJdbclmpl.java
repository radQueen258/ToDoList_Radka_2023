package Repositories.Task;

import Models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepositoryJdbclmpl implements TaskRepository {

    private Connection connection;
    private Statement statement;

    private static final String SQL_INSERT = "insert into tasks(user_id,task_name, description, deadline, status) values";
    private static final String SQL_SELECT = "select task_id, user_id, task_name, description, deadline, status from tasks where user_id = ?";

    private static final String SQL_SELECT_FROM_DRIVER = "select user_id, task_id, task_name, description, deadline from tasks";

    public TaskRepositoryJdbclmpl(Connection connection) {
        this.connection = connection;
    }


    public TaskRepositoryJdbclmpl(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public TaskRepositoryJdbclmpl() {

    }

    @Override
    public void saveTask(Task task) throws SQLException {
        String sql = SQL_INSERT + "(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,task.getUserId());
        preparedStatement.setString(2, task.getTaskName());
        preparedStatement.setString(3, task.getTaskDescription());
        preparedStatement.setDate(4, task.getTaskDeadline());
        preparedStatement.setString(5, task.getTaskStatus());
//        preparedStatement.setLong(5,task.getFileId());

        preparedStatement.executeUpdate();
        System.out.println("Task Executed");
    }

    @Override
    public List findByUser (long userId) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Task> userTasks = new ArrayList<>();

            while (resultSet.next()) {

                Task task = Task.builder()
                        .TaskId(resultSet.getLong("task_id"))
                        .UserId(resultSet.getLong("user_id"))
//                        .FileId(resultSet.getLong("file_id"))
                        .TaskName(resultSet.getString("task_name"))
                        .TaskDescription(resultSet.getString("description"))
                        .TaskDeadline(resultSet.getDate("deadline"))
                        .TaskStatus(resultSet.getString("status"))
                        .build();

                userTasks.add(task);
            }

            return userTasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Task findTaskById(long taskId) {
        Task task = null;
        try {
            String sqlFind = "select task_id, user_id, task_name, description, deadline,status  from tasks where task_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlFind);
            preparedStatement.setLong(1, taskId);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                task = Task.builder()
                        .TaskId(resultSet.getLong("task_id"))
                        .UserId(resultSet.getLong("user_id"))
//                        .FileId(resultSet.getLong("file_id"))
                        .TaskName(resultSet.getString("task_name"))
                        .TaskDescription(resultSet.getString("description"))
                        .TaskDeadline(resultSet.getDate("deadline"))
                        .TaskStatus(resultSet.getString("status"))
                        .build();


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    @Override
    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sqlTasks = "select * from tasks";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlTasks);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
              Task task = Task.builder()
                      .TaskId(resultSet.getLong("task_id"))
                      .TaskName(resultSet.getString("task_name"))
                      .TaskDescription(resultSet.getString("description"))
                      .TaskDeadline(resultSet.getDate("deadline"))
                      .build();
              tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    @Override
    public boolean deleteTaskById(long taskId) {
        boolean deleted;

        try {
            String sqlDelete = "DELETE FROM tasks WHERE task_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setLong(1,taskId);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deleted;
    }

    @Override
    public boolean markTaskAsFinished(long taskId) {
        boolean updated;

        try {
            String sqlUpdate = "UPDATE tasks SET status = 'finished' WHERE task_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setLong(1, taskId);
            updated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return updated;
    }

    @Override
    public List findAll() {
        try{
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVER);
            List<Task> result = new ArrayList<>();

            while (resultSet.next()) {
                Task task = Task.builder()
                        .UserId(resultSet.getLong("user_id"))
                        .TaskId(resultSet.getLong("task_id"))
                        .TaskName(resultSet.getString("task_name"))
                        .TaskDescription(resultSet.getString("description"))
                        .TaskDeadline(resultSet.getDate("deadline"))
                        .build();

                result.add(task);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}