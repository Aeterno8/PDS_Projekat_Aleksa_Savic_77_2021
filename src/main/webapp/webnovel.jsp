<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.webnovelapp.model.Webnovel" %>
<%@ page import="com.example.webnovelapp.service.WebnovelService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.webnovelapp.model.Chapter" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.example.webnovelapp.beans.UserBean" %>
<%@ page import="com.example.webnovelapp.service.UserService" %>
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

    Long webnovelId = null;
    try {
        webnovelId = Long.parseLong(request.getParameter("id"));
    } catch (NumberFormatException e) {
        response.sendRedirect("index.jsp");
        return;
    }

    WebnovelService webnovelService = new WebnovelService();
    Optional<Webnovel> webnovelOptional = webnovelService.getWebnovelById(webnovelId);

    if (!webnovelOptional.isPresent()) {
        response.sendRedirect("index.jsp");
        return;
    }

    Webnovel webnovel = webnovelOptional.get();
    List<Chapter> chapters = webnovel.getChapters();

    int pageSize = 100;
    int totalChapters = chapters.size();
    int totalPages = (int) Math.ceil((double) totalChapters / pageSize);

    int currentPage = 1;
    if (request.getParameter("page") != null) {
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
            if (currentPage < 1 || currentPage > totalPages) {
                response.sendRedirect("error.jsp");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
            return;
        }
    }

    int startIndex = (currentPage - 1) * pageSize;
    int endIndex = Math.min(startIndex + pageSize, totalChapters);

    List<Chapter> chaptersOnPage = chapters.subList(startIndex, endIndex);

    webnovelService.close();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= webnovel.getTitle() %> - Webnovels</title>
    <link rel="stylesheet" href="navbar.css">
    <link rel="stylesheet" href="webnovel.css">
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
        <h2><%= webnovel.getTitle() %></h2>

        <div class="novel-container">
            <a href="chapter.jsp?id=<%= webnovel.getId() %>">
                <img class="novel-image" src="<%= webnovel.getCoverUrl() %>" alt="Cover Image">
            </a>
        </div>

        <div class="pagination-options">
            <div class="pagination-info">
                <span>Total Chapters: <%= totalChapters %></span>
                <span>Page <%= currentPage %> of <%= totalPages %></span>
            </div>
            <div class="pagination-links">
                <% if (currentPage > 1) { %>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=1" class="pagination-link">First</a>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=<%= currentPage - 1 %>" class="pagination-link">Previous</a>
                <% } %>
                <% if (currentPage < totalPages) { %>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=<%= currentPage + 1 %>" class="pagination-link">Next</a>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=<%= totalPages %>" class="pagination-link">Last</a>
                <% } %>
            </div>
        </div>

        <div class="toc">
            <h3>Table of Contents</h3>
            <% for (Chapter chapter : chaptersOnPage) { %>
            <div class="toc-item">
                <a href="chapter.jsp?id=<%= webnovel.getId() %>&chapterId=<%= chapter.getId() %>"><%= chapter.getTitle() %></a>
            </div>
            <% } %>
        </div>

        <div class="pagination-options">
            <div class="pagination-info">
                <span>Total Chapters: <%= totalChapters %></span>
                <span>Page <%= currentPage %> of <%= totalPages %></span>
            </div>
            <div class="pagination-links">
                <% if (currentPage > 1) { %>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=1" class="pagination-link">First</a>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=<%= currentPage - 1 %>" class="pagination-link">Previous</a>
                <% } %>
                <% if (currentPage < totalPages) { %>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=<%= currentPage + 1 %>" class="pagination-link">Next</a>
                <a href="webnovel.jsp?id=<%= webnovel.getId() %>&page=<%= totalPages %>" class="pagination-link">Last</a>
                <% } %>
            </div>
        </div>

    </div>
</main>

</body>
</html>