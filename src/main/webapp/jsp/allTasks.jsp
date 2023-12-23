<%--
  Created by IntelliJ IDEA.
  User: radqueen
  Date: 20.12.2023
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="Models.Task" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Tasks</title>
  <style>
    .overlay {

      height: 100%;
      width: 0;
      position: fixed;
      z-index: 1;
      left: 0;
      top: 0;
      background-color: rgba(255, 255, 255, 0.8);
      background-color: rgba(255, 255, 255, 0.8);
      overflow-x: hidden;
      transition: 0.5s;
    }

    /* Position the content inside the overlay */
    .overlay-content {
      position: relative;
      top: 25%;
      width: 100%;
      text-align: center;
      margin-top: 30px;
    }

    /* The navigation links inside the overlay */
    .overlay a {
      padding: 8px;
      text-decoration: none;
      font-size: 36px;
      color: black;
      display: block;
      transition: 0.3s;
    }


    .overlay a:hover, .overlay a:focus {
      color: #181717;
    }


    .overlay .closebtn {
      position: absolute;
      top: 20px;
      right: 45px;
      font-size: 60px;
    }


    @media screen and (max-height: 450px) {
      .overlay a {font-size: 20px}
      .overlay .closebtn {
        font-size: 40px;
        top: 15px;
        right: 35px;
      }
    }


    /*    ---------------------------------------------------*/
    /* General Styles */

    .container {
      max-width: 100%;
      overflow-x: auto;
      padding: 20px;
    }

    .responsive-table {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #ccc;
    }

    .responsive-table th,
    .responsive-table td {
      padding: 12px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }

    .responsive-table th {
      background-color: #f5f5f5;
      font-weight: bold;
    }

    /* Table Header Styles */

    .table-header {
      background-color: #f5f5f5;
      font-weight: bold;
    }

    /* Table Body Styles */

    tbody tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    tbody tr:hover {
      background-color: #f0f0f0;
    }

    /* Actions Styles */

    .action-buttons {
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .btn-delete {
      padding: 8px 12px;
      background-color: #ff0000;
      color: #fff;
      text-decoration: none;
      border-radius: 4px;
    }

    .btn-delete:hover {
      background-color: #cc0000;
    }

  </style>


</head>
<body>

<!-- The overlay -->
<div id="myNav" class="overlay">

  <!-- Button to close the overlay navigation -->
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>

  <div class="overlay-content">
    <a href="/html/allUsers.jsp">Home</a>
    <a href="<%=request.getContextPath()%>/logoutUser">Logout</a>
  </div>

</div>

<div class ="container">
  <table class="responsive-table">
    <thead>
    <tr class="table=header">
      <th scope="col">User ID</th>
      <th scope="col">Task ID</th>
      <th scope="col">Task Name</th>
      <th scope="col">Description</th>
      <th scope="col">Deadline</th>
      <th scope="col">Status</th>
      <th scope="col">Actions</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${allTasks}" var="task">
      <tr class="fw-normal" id="${task.taskId}">
        <td scope="row">${task.userId}</td>
        <td scope="row">${task.taskId}</td>
        <td class="align-middle">${task.taskName}</td>
        <td class="align-middle">${task.taskDescription}</td>
        <td class="align-middle">${task.taskDeadline}</td>
        <td class="align-middle">${task.taskStatus}</td>
        <td class="align-middle">
          <div class="action-buttons">
            <a href="adminDelete?task_id=<c:out value="${task.taskId}"/>" class="btn-delete">Delete</a>
          </div>
        </td>
      </tr>
    </c:forEach>

    </tbody>
  </table>
</div>

<span style="font-size:30px;cursor:pointer" onclick="openNav()">MENU</span>

<script>
  //Function to handle the menu appearance
  function openNav() {
    document.getElementById("myNav").style.width = "100%";
  }

  function closeNav() {
    document.getElementById("myNav").style.width = "0%";
  }


</script>
</body>
</html>
