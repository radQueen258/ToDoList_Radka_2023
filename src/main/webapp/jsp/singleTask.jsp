<%@ page import="java.util.List" %>
<%@ page import="Models.Task" %>
<%@ page import="Models.Filees" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
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

    /* Profile card styles */
    .profile-card {
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      padding: 30px;
      width: 400px;
    }

    .profile-card h1 {
      font-size: 2em;
      margin: 0;
      color: #333;
    }

    .profile-card p {
      margin-top: 5px;
      margin-bottom: 10px;
      color: #555;
    }

    .profile-card hr {
      border: 0;
      border-top: 1px solid #ccc;
      margin: 20px 0;
    }

  </style>
</head>
<body>

<c:if test="${not empty details}" >
  <div class="profile-card">
    <h1>${details.taskName}</h1>
    <p>Description: ${details.taskDescription}</p>
    <hr>
    <c:if test="${not empty details.fileId}">
      <a href="viewFile?fileId=${details.fileId}" target="_blank" class="file-link">View File</a>
    </c:if>
    <hr>
    <p>Deadline: ${details.taskDeadline}</p>


      <%--    <a class="edit-profile-link" href="/edit" role="button">Edit Profile</a>--%>
  </div>
</c:if>

<%--<c:forEach items="${details}" var="task">--%>
<%--  --%>
<%--</c:forEach>--%>


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
