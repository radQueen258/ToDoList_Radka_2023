//package Servlets;
//
//import Repositories.Task.TaskRepository;
//import Repositories.Task.TaskRepositoryJdbclmpl;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/deleteTask")
//public class DeleteTaskServlet extends HttpServlet {
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long taskId = Long.parseLong(request.getParameter("task_id"));
//        System.out.println(taskId);
//
//        TaskRepository taskRepository = new TaskRepositoryJdbclmpl();
//        boolean deleted = taskRepository.deleteTaskById(taskId);
//
//        if (deleted) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write("Task Deleted Successfully");
//        } else {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("Failed to Delete Task");
//        }
//    }
//}
