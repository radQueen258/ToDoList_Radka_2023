<%@ page import="java.util.List" %>
<%@ page import="Models.Task" %>
<%@ page import="Models.User" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>To Do List</title>
    <style>
        .gradient-custom-2 {

            background: #68186b;

            background: -webkit-linear-gradient(
                    to right,
                    rgb(185, 64, 246),
                    rgb(129, 80, 252)
            );


            background: linear-gradient(
                    to right,
                    rgb(185, 64, 246),
                    rgb(129, 80, 252)
            );
        }

        .mask-custom {
            background: rgba(24, 24, 16, 0.2);
            border-radius: 2em;
            backdrop-filter: blur(25px);
            border: 2px solid rgba(255, 255, 255, 0.05);
            background-clip: padding-box;
            box-shadow: 10px 10px 10px rgba(46, 54, 68, 0.03);
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #343a40;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        .action-buttons {
            display: flex;
            align-items: center;
        }

        .action-buttons a {
            padding: 6px 12px;
            margin-right: 5px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease-in-out;
        }

        .action-buttons a.btn-delete {
            background-color: #dc3545;
            color: white;
        }

        .action-buttons a.btn-finished {
            background-color: #28a745;
            color: white;
        }

        .action-buttons a.btn-view {
            background-color: #20307e;
            color: white;
        }

        .action-buttons a:hover {
            filter: brightness(90%);
        }

        .divider {
            height: 1px;
            width: 6px;
            background-color: transparent;
            margin: 0 8px;
        }

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
    </style>
</head>
<body>

<section class="vh-100 gradient-custom-2">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-12 col-xl-10">

                <div class="card mask-custom">
                    <div class="card-body p-4 text-white">

                        <div class="text-center pt-3 pb-2">
                            <img src="/images/img.png" alt="Check" width="60">
                            <h2 class="my-4">Task List</h2>
                        </div>

                        <table class="table table-striped table-dark">
                            <thead>
                            <tr class="fw-normal">
                                <th scope="col">Task ID</th>
                                <th scope="col">Task Name</th>
                                <th scope="col">Description</th>
                                <th scope="col">Deadline</th>
                                <th scope="col">Status</th>
                                <th scope="col">Actions</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${taskJsp}" var="task">
                                <tr class="fw-normal" id="${task.taskId}">
                                    <td scope="row">${task.taskId}</td>
                                    <td class="align-middle">${task.taskName}</td>
                                    <td class="align-middle">${task.taskDescription}</td>
                                    <td class="align-middle">${task.taskDeadline}</td>
                                    <td class="align-middle">${task.taskStatus}</td>
                                    <td class="align-middle">
                                        <div class="action-buttons">
                                            <a href="deleteTask?task_id=<c:out value="${task.taskId}"/>" class="btn-delete">Delete</a>
                                            <span class="divider"></span>
                                            <a href="detailsTask?task_id=<c:out value="${task.taskId}"/>" class="btn-view">View Task</a>
                                            <span class="divider"></span>
                                            <a href="/markFinished?task_id=<c:out value="${task.taskId}"/>" class="btn-finished">Mark as Finished</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                    </div>
                </div>

            </div>
        </div>
    </div>
</section>

<!-- The overlay -->
<div id="myNav" class="overlay">

    <!-- Button to close the overlay navigation -->
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>

    <!-- Overlay content -->
    <div class="overlay-content">
        <a href="/jsp/HomePage.jsp">Home</a>
        <a href="/taskByUser">Tasks</a>
        <a href="/jsp/CreateTask.jsp">Create Task</a>
        <a href="/profile">Profile</a>
        <a href="<%=request.getContextPath()%>/logoutUser">Logout</a>
    </div>

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




    <%--function deleteTask(taskId) {--%>
    <%--    console.log("taskId:");--%>
    <%--    console.log(taskId);--%>
    <%--    let path = 'http://localhost:8080/tasks/${taskId}';--%>
    <%--    fetch(path, {--%>
    <%--        method: 'DELETE'--%>
    <%--    })--%>
    <%--        .then(response => {--%>
    <%--            document.getElementById('${taskId}').remove();--%>
    <%--            if (response.ok) {--%>
    <%--                console.log('Task with ID ${taskId} deleted successfully');--%>
    <%--                document.getElementById('${taskId}').remove();--%>
    <%--            } else {--%>
    <%--                console.error('Failed to delete task with ID ${taskId}');--%>
    <%--            }--%>
    <%--        })--%>
    <%--        .catch(error => {--%>
    <%--            console.error('Error deleting task:', error);--%>
    <%--        });--%>
    <%--}--%>

    <%--function markAsFinished(taskId) {--%>
    <%--    fetch('http://localhost:8080/tasks/${taskId}/finish', {--%>
    <%--        method: 'PUT'--%>
    <%--    })--%>
    <%--        .then(response => {--%>
    <%--            if (response.ok) {--%>
    <%--                console.log('Task ID ${taskId} marked as finished');--%>
    <%--                document.getElementById('taskStatus-${taskId}').innerText = 'Finished';--%>
    <%--            } else {--%>
    <%--                console.error(`Failed to mark task with ID ${taskId} as finished`);--%>
    <%--            }--%>
    <%--        })--%>
    <%--        .catch(error => {--%>
    <%--            console.error('Error marking task as finished:', error);--%>
    <%--        });--%>
    <%--}--%>
</script>

</body>
</html>




