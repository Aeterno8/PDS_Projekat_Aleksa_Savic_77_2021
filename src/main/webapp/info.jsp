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
    <title>Info Page</title>
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
                <li><a href="profile.jsp" class="navbar-item">Profile</a></li>
                <li><a href="info.jsp" class="navbar-item active">Info</a></li>
                <li><a href="chat.jsp" class="navbar-item">Chat</a></li>
                <li><a href="logout.jsp" class="navbar-item">Logout</a></li>
                <% if (isAdmin) { %>
                <li><a href="admin.jsp" class="navbar-item">Admin</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
</header>

<main class="content">
    <div class="container">
        <h2>Instructions and Help</h2>

        <p>Welcome, <%= user.getUsername() %>! Here are some instructions for using our Webnovels application:</p>

        <ul>
            <li>Explore the "Home" page to discover new webnovels.</li>
            <li>Visit your "Profile" page to see your account info.</li>
            <li>Check out the "Chat" page to interact with other readers.</li>
            <% if (isAdmin) { %>
            <li>As an admin, you have access to additional features in the "Admin" section.</li>
            <% } %>
        </ul>
    </div>
</main>

</body>
</html>