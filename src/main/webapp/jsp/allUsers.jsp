<%--
  Created by IntelliJ IDEA.
  User: radqueen
  Date: 20.12.2023
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="Models.User" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
    <style>
        .overlay {

            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            background-color: rgba(120, 25, 74, 0.8);
            background-color: rgba(120,25,74, 1);
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
            color: #818181;
            display: block;
            transition: 0.3s;
        }


        .overlay a:hover, .overlay a:focus {
            color: #f1f1f1;
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

<!-- The overlay -->
<div id="myNav" class="overlay">

    <!-- Button to close the overlay navigation -->
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>

    <div class="overlay-content">
        <a href="/jsp/allUsers.jsp">Home</a>
        <a href="/jsp/LoginPage.jsp" id="logoutLink">Logout</a>
    </div>

</div>
<div>
    <table class="table">
        <thead>
        <tr class="fw-normal">
            <th scope="col">ID</th>
            <th scope="col">NICKNAME</th>
            <th scope="col">EMAIL</th>
            <th scope="col">REGISTRATION</th>
            <th scope="col">Actions</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <td class="align-middle">${user.userId}</td>
                <td class="align-middle">${user.userNickname}</td>
                <td class="align-middle">${user.userEmail}</td>
                <td class="align-middle">${user.userRegistration}</td>
                <td class="align-middle">
                    <a href="loginAdmin?user_id=<c:out value="${user.userId}"/> " class="btn-finished">Login As Admin</a>
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
