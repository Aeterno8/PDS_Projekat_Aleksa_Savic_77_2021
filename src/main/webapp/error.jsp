<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.io.IOException" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="java.rmi.RemoteException" %>
<%@ page import="java.rmi.UnmarshalException" %>
<%@ page import="java.io.NotSerializableException" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Webnovels</title>
    <link rel="stylesheet" href="index.css">
    <link rel="stylesheet" href="navbar.css">
</head>
<body>

<header>
    <nav class="navbar">
        <div class="container">
            <div class="navbar-brand">
                <a href="index.jsp" class="navbar-logo">Webnovels</a>
            </div>
            <ul class="navbar-menu">
                <li><a href="index.jsp" class="navbar-item active">Home</a></li>
                <li><a href="profile.jsp" class="navbar-item">Profile</a></li>
                <li><a href="info.jsp" class="navbar-item">Info</a></li>
                <li><a href="chat.jsp" class="navbar-item">Chat</a></li>
            </ul>
        </div>
    </nav>
</header>

<main class="content">
    <div class="container">
        <h2>Error</h2>
        <p>An unexpected error occurred. Please try again later.</p>
        <p>If this problem persists, please contact support.</p>
    </div>
</main>

</body>
</html>