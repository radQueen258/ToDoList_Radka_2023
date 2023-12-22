<html>
<head>
    <title>Welcome to Our Site</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-image: linear-gradient(to bottom right, #ff80bf, #9980ff);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .container {
            text-align: center;
            color: black;
        }

        .jumbotron {
            background-color: rgba(255, 255, 255, 0.8); /* Transparent white */
            border-radius: 15px; /* Add some rounded corners */
            padding: 30px; /* Adjust padding as needed */
            max-width: 600px; /* Set maximum width */
            margin: auto; /* Center horizontally */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Add a subtle shadow */
        }

        .login-btn, .register-btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            color: #fff;
            background-color: #020e4f;
            transition: background-color 0.3s;
        }

        .login-btn:hover, .register-btn:hover {
            background-color: #20307e;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="jumbotron">
        <h1>Welcome to Our Site</h1>
        <p>Explore and discover amazing things!</p>
        <div class="buttons">
            <a href="/jsp/LoginPage.jsp" class="login-btn">Login</a>
            <a href="/jsp/SignUpPage.jsp" class="register-btn">Register</a>
        </div>
    </div>
</div>

</body>
</html>
