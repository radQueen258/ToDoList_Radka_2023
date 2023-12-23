<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%--  <link rel="stylesheet" href="/css/welcomePage.css">--%>
  <title>Welcome to Our Page</title>

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


    .jumbotron {
      text-align: center;
      background-color: rgba(255, 255, 255, 0.8); /* Transparent white */
      border-radius: 15px; /* Add some rounded corners */
      padding: 30px; /* Adjust padding as needed */
      max-width: 600px; /* Set maximum width */
      margin: auto; /* Center horizontally */
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Add a subtle shadow */
    }

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
  </style>


</head>
<body>

<!-- The overlay -->
<div id="myNav" class="overlay">

  <!-- Button to close the overlay navigation -->
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>

  <div class="overlay-content">
    <a href="/jsp/HomePage.jsp">Home</a>
    <a href="/taskByUser">Tasks</a>
    <a href="/jsp/CreateTask.jsp">Create Task</a>
    <a href="/profile">Profile</a>
    <a href="<%=request.getContextPath()%>/logoutUser">Logout</a>
  </div>

</div>

<div class="jumbotron jumbotron-fluid">
  <div class="container">
    <h1 class="display-4">Welcome Back!</h1>
    <p class="lead">"Organizing your tasks is the key to productivity and success."</p>
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

