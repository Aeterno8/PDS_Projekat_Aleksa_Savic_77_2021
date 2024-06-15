<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Optional" %>
<%@ page import="com.example.webnovelapp.beans.UserBean" %>
<%@ page import="com.example.webnovelapp.service.UserService" %>
<%@ page import="java.rmi.registry.LocateRegistry" %>
<%@ page import="java.rmi.registry.Registry" %>
<%@ page import="com.example.webnovelapp.rmi.UserAuthenticationService" %>
<%@ page import="com.example.webnovelapp.model.User" %>

<%
    String sessionId = (String) session.getAttribute("sessionId");

    if (sessionId == null || sessionId.isEmpty()) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserService userService = new UserService();
    Optional<UserBean> userBeanOptional = userService.getUserBySessionId(sessionId);

    if (!userBeanOptional.isPresent()) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserBean user = userBeanOptional.get();

    boolean isAdmin = (user.getId() == 1);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
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
                <li><a href="index.jsp" class="navbar-item">Home</a></li>
                <li><a href="profile.jsp" class="navbar-item active">Profile</a></li>
                <li><a href="info.jsp" class="navbar-item">Info</a></li>
                <li><a href="chat.jsp" class="navbar-item">Chat</a></li>
                <% if (isAdmin) { %>
                <li><a href="admin.jsp" class="navbar-item">Admin</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
</header>

<main class="content">
    <div class="container">
        <h2>User Profile</h2>

        <div>
            <p><strong>Username:</strong> <%= user.getUsername() %></p>
            <p><strong>Email:</strong> <%= user.getEmail() %></p>
            <p><strong>Name:</strong> <%= user.getName() %></p>
            <p><strong>Surname:</strong> <%= user.getSurname() %></p>
        </div>
    </div>
</main>

</body>
</html>