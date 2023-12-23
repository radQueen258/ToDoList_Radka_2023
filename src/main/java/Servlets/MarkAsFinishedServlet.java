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
//@WebServlet("/markAsFinished")
//public class MarkAsFinishedServlet extends HttpServlet {
//
//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long taskId = Long.parseLong(request.getParameter("task_id"));
//
//        TaskRepository taskRepository = new TaskRepositoryJdbclmpl();
//
//        boolean updated = taskRepository.markTaskAsFinished(taskId);
//
//        if (updated) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write("Task marked as finished");
//        } else {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("Failed to update the task status");
//        }
//    }
//}
