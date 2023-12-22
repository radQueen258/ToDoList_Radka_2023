<%@ page import="java.util.List" %>
<%@ page import="Models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
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
        /* Basic styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            background-image: linear-gradient(to bottom right, #ff80bf, #9980ff);
        }

        /* Form styles */
        .edit-form {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 400px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-group input[type="email"],
        .form-group input[type="password"],
        .form-group input[type="text"] {
            width: calc(100% - 12px);
            padding: 6px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }

        .form-group input[type="email"]:focus,
        .form-group input[type="password"]:focus,
        .form-group input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
        }

        .form-group small {
            font-size: 12px;
            color: #888;
        }

        .submit-btn {
            display: block;
            width: 100%;
            padding: 8px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .submit-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<c:forEach items="${edit}" var="user">

    <form action="/edit" method="post" class="edit-form">
        <div class="form-group">
            <label for="exampleInputEmail1">Email </label>
            <input type="email" name="email" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Email" value="${user.userEmail}">
            <small id="emailHelp">We'll never share your email with anyone else.</small>
        </div>

        <div class="form-group">
            <label for="NewInputPassword">New Password</label>
            <input type="password" name="newPass" id="NewInputPassword" placeholder="New Password" value="${user.userPassword}">
        </div>

        <div class="form-group">
            <label for="nickname">Nickname</label>
            <input type="text" id="nickname" name="nickname" placeholder="Nickname" value="${user.userNickname}">
        </div>

        <button type="submit" class="submit-btn">Submit</button>
    </form>
</c:forEach>

<div id="myNav" class="overlay">

    <!-- Button to close the overlay navigation -->
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>

    <div class="overlay-content">
        <a href="/jsp/HomePage.jsp">Home</a>
        <a href="/taskByUser">Tasks</a>
        <a href="/jsp/CreateTask.jsp">Create Task</a>
        <a href="/profile">Profile</a>
        <a href="/logoutUser" id="logoutLink">Logout</a>
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


</script>
</body>
</html>
