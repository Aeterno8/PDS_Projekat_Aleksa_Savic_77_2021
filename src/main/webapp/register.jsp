<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="register.css">
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

        .login-link {
            margin-top: 20px;
            text-align: center;
        }

        .login-link a {
            color: #4CAF50;
            text-decoration: none;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="register-container">
        <h2>Register</h2>
        <form action="register" method="post" class="register-form">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="surname">Surname:</label>
                <input type="text" id="surname" name="surname" required>
            </div>
            <div class="form-group">
                <button type="submit">Register</button>
            </div>
        </form>

        <div id="message">
            <%= request.getAttribute("message") != null ? (String) request.getAttribute("message") : "" %>
        </div>

        <div id="instructions-popup" class="popup">
            <div class="popup-content">
                <span class="close-btn" onclick="closePopup()">&times;</span>
                <h3>Instructions</h3>
                <p>Fill out the form with your desired username, password, email, name, and surname.</p>
                <p>Click on the "Register" button to create your account.</p>
            </div>
        </div>

        <div class="login-link">
            <p>Already have an account? <a href="login.jsp">Login</a></p>
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