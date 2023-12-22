<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Create Task</title>
    <%--  <link rel="stylesheet" type="text/css" href="/css/CreateTask.css">--%>

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

        .task-details {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 400px;
        }

        .task-details h1 {
            font-size: 2em;
            margin: 0;
            color: #333;
        }

        .task-details label {
            margin-top: 5px;
            margin-bottom: 10px;
            color: #555;
        }
    </style>
</head>
<body>



<form id="taskForm" action="/task" method="POST" enctype="multipart/form-data">
    <div class="task-details">
        <h1>New Task</h1>
        <hr>
        <div class="form-group">
            <label >Task Name:</label>
            <input type="text" id="taskName" placeholder="Task Name" name="task_name" required>
        </div>
        <br>

        <div class="form-group">
            <label for="taskDescription">Task Description:</label>
            <textarea id="taskDescription" placeholder="Your Task Description" name="description" rows="4" required></textarea>
        </div>
        <br>

        <div class="form-group">
            <label for="taskDeadline">Deadline:</label>
            <input type="date" id="taskDeadline" name="deadline" required>
        </div>
        <br>

        <div class="form-group mt-3">
            <label for="file">Upload File:</label>
            <input type="file" id="file" name="file">
        </div>
        <br>

        <input type="submit" value="Submit Task" class="btn-btn-primary">

    </div>
</form>

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


