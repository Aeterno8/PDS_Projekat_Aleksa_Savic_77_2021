<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="5;url=login.jsp">
    <title>Logout</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        .container {
            margin-top: 50px;
            text-align: center;
        }
        .logout-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="logout-container">
        <h2>Logout</h2>
        <p>You have been successfully logged out.</p>
        <p>If you are not redirected automatically, <a href="login.jsp">click here</a>.</p>
    </div>
</div>
</body>
</html>