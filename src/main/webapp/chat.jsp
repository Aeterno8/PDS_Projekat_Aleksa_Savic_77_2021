<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Optional" %>
<%@ page import="com.example.webnovelapp.beans.UserBean" %>
<%@ page import="com.example.webnovelapp.service.UserService" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

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
    <title>Chat Room</title>
    <link rel="stylesheet" href="chat.css">
    <link rel="stylesheet" href="navbar.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        var websocket;

        $(document).ready(function() {
            var sessionId = '<%= session.getAttribute("sessionId") %>';

            if (!sessionId) {
                window.location.replace('login.jsp');
            }

            connectWebSocket(sessionId);

            $('#messageForm').submit(function(event) {
                event.preventDefault();
                var message = $('#messageInput').val().trim();
                if (message.length > 0) {
                    sendMessage(message);
                    $('#messageInput').val('');
                }
            });

            scrollToBottom();
        });

        function connectWebSocket(sessionId) {
            websocket = new WebSocket('ws://localhost:8080/webnovelapp_war_exploded/chat?sessionId=' + sessionId);

            websocket.onopen = function(event) {
                console.log('WebSocket connected');
            };

            websocket.onmessage = function(event) {
                var message = JSON.parse(event.data);
                displayMessage(message);
            };

            websocket.onerror = function(event) {
                console.error('WebSocket error: ' + event.data);
            };

            websocket.onclose = function(event) {
                console.log('WebSocket closed');
            };
        }

        function sendMessage(message) {
            websocket.send(message);
        }

        function displayMessage(message) {
            var currentUser = '<%= user.getUsername() %>';
            var messageClass = message.user.username === currentUser ? 'self' : '';
            console.log(message);

            var messageElement = '<div class="message ' + messageClass + '">' +
                '<div class="message-content">' + message.content + '</div>' +
                '<div class="message-info">' + message.timestamp + ' [' + message.user.username + ']</div>' +
                '</div>';

            $('#chatMessages').append(messageElement);
            console.log(messageElement);
            scrollToBottom();
        }

        var scrollTimeout;

        function scrollToBottom() {
            var delay = 200;

            clearTimeout(scrollTimeout);

            scrollTimeout = setTimeout(function() {
                $('html, body').animate({ scrollTop: $(document).height() }, 'slow');
            }, delay);
        }

    </script>
</head>
<body>

<div class="all">

    <header>
        <nav class="navbar">
            <div class="container">
                <div class="navbar-brand">
                    <a href="index.jsp" class="navbar-logo">Webnovels</a>
                </div>
                <ul class="navbar-menu">
                    <li><a href="index.jsp" class="navbar-item">Home</a></li>
                    <li><a href="profile.jsp" class="navbar-item">Profile</a></li>
                    <li><a href="info.jsp" class="navbar-item">Info</a></li>
                    <li><a href="chat.jsp" class="navbar-item active">Chat</a></li>
                    <li><a href="logout.jsp" class="navbar-item">Logout</a></li>
                    <% if (isAdmin) { %>
                    <li><a href="admin.jsp" class="navbar-item">Admin</a></li>
                    <% } %>
                </ul>
            </div>
        </nav>
    </header>

    <div id="chatContainer" class="container">
        <div id="chatMessages" class="chat-messages"></div>
        <form id="messageForm" class="message-form">
            <input type="text" id="messageInput" placeholder="Type your message..." class="message-input">
            <button type="submit" class="send-button">Send</button>
        </form>
    </div>

</div>

</body>
</html>
