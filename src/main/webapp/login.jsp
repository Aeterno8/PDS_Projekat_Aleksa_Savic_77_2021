<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="login.css">
    <style>
        .popup {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .popup-content {
            background-color: white;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            max-width: 80%;
            max-height: 80%;
            overflow: auto;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
        }

        .instructions-btn {
            background-color: transparent;
            color: inherit;
            border: none;
            cursor: pointer;
            font-size: 16px;
            outline: none;
        }

        .instructions-btn:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="login-container">
        <h2>Login</h2>
        <form action="login" method="post" class="login-form">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <div class="register-link">
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </div>
        <div id="error-message">
            <%= request.getAttribute("message") != null ? (String) request.getAttribute("message") : "" %>
        </div>
        <div id="instructions-popup" class="popup">
            <div class="popup-content">
                <span class="close-btn" onclick="closePopup()">&times;</span>
                <h3>Instructions</h3>
                <p>Enter your username and password to login.</p>
                <p>If you don't have an account, click on "Register here" to create a new account.</p>
            </div>
        </div>
        <button class="instructions-btn" onclick="openPopup()">?</button>
    </div>
</div>

<script>
    function openPopup() {
        document.getElementById("instructions-popup").style.display = "block";
    }

    function closePopup() {
        document.getElementById("instructions-popup").style.display = "none";
    }
</script>

</body>
</html>