package Repositories.Task;

import Models.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskRepository {
    void saveTask (Task task) throws SQLException;
    List findByUser(long userId);

    Task findTaskById(long taskId);

    boolean deleteTaskById(long taskId);
    boolean markTaskAsFinished(long taskId);

    List findAll();
}
