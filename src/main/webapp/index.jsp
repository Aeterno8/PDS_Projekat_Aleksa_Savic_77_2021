<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.webnovelapp.model.Webnovel" %>
<%@ page import="com.example.webnovelapp.service.WebnovelService" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.example.webnovelapp.beans.UserBean" %>
<%@ page import="com.example.webnovelapp.service.UserService" %>

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
    <title>Webnovels</title>
    <link rel="stylesheet" href="index.css">
    <link rel="stylesheet" href="navbar.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                <% if (isAdmin) { %>
                <li><a href="admin.jsp" class="navbar-item">Admin</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
</header>


<main class="content">
    <div class="container">
        <h2>Welcome, <%= user.getUsername() %>!</h2>

        <div class="novel-list" id="novelList">
            <% WebnovelService webnovelService = new WebnovelService();
                List<Webnovel> novels = webnovelService.getFirstFiveWebnovels();
                for (Webnovel novel : novels) { %>
            <div class="novel-container" data-novel-id="<%= novel.getId() %>">
                <a href="webnovel.jsp?id=<%= novel.getId() %>">
                    <img class="novel-image" src="<%= novel.getCoverUrl() %>" alt="Cover Image">
                    <div class="novel-title"><%= novel.getTitle() %></div>
                </a>
            </div>
            <% }
                webnovelService.close(); %>
        </div>
    </div>
</main>

<script>
    $(document).ready(function () {
        var loading = false;

        function loadMoreWebnovels(lastWebnovelId) {
            if (loading) return;
            loading = true;

            $.ajax({
                url: 'LoadMoreWebnovelsServlet',
                method: 'GET',
                data: { lastWebnovelId: lastWebnovelId },
                success: function (response) {
                    if (response.trim().length == 0) {
                        $(window).off('scroll');
                        return;
                    }
                    $('#novelList').append(response);
                    loading = false;
                },
                error: function () {
                    console.error('Error loading webnovels');
                    loading = false;
                }
            });
        }

        $(window).scroll(function () {
            if ($(window).scrollTop() + $(window).height() >= $(document).height() - 200) {
                var lastWebnovelId = $('.novel-container').last().data('novel-id');
                loadMoreWebnovels(lastWebnovelId);
            }
        });

        var typingTimer;
        var doneTypingInterval = 500;
        $('#searchInput').on('keyup', function () {
            clearTimeout(typingTimer);
            typingTimer = setTimeout(searchWebnovels, doneTypingInterval);
        });

        function searchWebnovels() {
            var searchText = $('#searchInput').val().trim();
            $.ajax({
                url: 'SearchWebnovelsServlet',
                method: 'GET',
                data: { query: searchText },
                success: function (response) {
                    $('#novelList').html(response);
                },
                error: function () {
                    console.error('Error searching webnovels');
                }
            });
        }
    });
</script>

</body>
</html>