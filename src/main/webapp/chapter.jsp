<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.webnovelapp.model.Chapter" %>
<%@ page import="com.example.webnovelapp.service.WebnovelService" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.io.IOException" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="com.example.webnovelapp.service.UserService" %>
<%@ page import="com.example.webnovelapp.beans.UserBean" %>

<%
    Long chapterId = Long.valueOf(request.getParameter("id"));

    WebnovelService webnovelService = new WebnovelService();
    Optional<Chapter> chapterOptional = webnovelService.getChapterById(chapterId);

    if (chapterOptional.isPresent()) {
        Chapter chapter = chapterOptional.get();

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
    <title>Chapter: <%= chapter.getTitle() %></title>
    <link rel="stylesheet" href="chapter.css">
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
                <% if (isAdmin) { %>
                <li><a href="admin.jsp" class="navbar-item">Admin</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
</header>

<main class="content">
    <div class="container">
        <div class="chapter-navigation">
            <div class="prev-button">
                <%
                    Optional<Long> previousChapterIdOpt = webnovelService.getPreviousChapterId(chapter.getId());
                    if (previousChapterIdOpt.isPresent()) {
                        Long previousChapterId = previousChapterIdOpt.get();
                %>
                <a href="chapter.jsp?id=<%= previousChapterId %>">
                    <button>Previous</button>
                </a>
                <% } %>
            </div>

            <div class="toc-button">
                <a href="toc.jsp">
                    <button>Table of Contents</button>
                </a>
            </div>

            <div class="next-button">
                <%
                    Optional<Long> nextChapterIdOpt = webnovelService.getNextChapterId(chapter.getId());
                    if (nextChapterIdOpt.isPresent()) {
                        Long nextChapterId = nextChapterIdOpt.get();
                %>
                <a href="chapter.jsp?id=<%= nextChapterId %>">
                    <button>Next</button>
                </a>
                <% } %>
            </div>
        </div>

        <div class="chapter-content" id="chapterContent">
            <%= chapter.getContent() %>
        </div>

        <div class="chapter-navigation">
            <div class="prev-button">
                <%
                    if (previousChapterIdOpt.isPresent()) {
                        Long previousChapterId = previousChapterIdOpt.get();
                %>
                <a href="chapter.jsp?id=<%= previousChapterId %>">
                    <button>Previous</button>
                </a>
                <% } %>
            </div>

            <div class="toc-button">
                <a href="toc.jsp">
                    <button>Table of Contents</button>
                </a>
            </div>

            <div class="next-button">
                <%
                    if (nextChapterIdOpt.isPresent()) {
                        Long nextChapterId = nextChapterIdOpt.get();
                %>
                <a href="chapter.jsp?id=<%= nextChapterId %>">
                    <button>Next</button>
                </a>
                <% } %>
            </div>
        </div>

    </div>
</main>

<svg xmlns="http://www.w3.org/2000/svg" class="settings-icon" viewBox="0 0 24 24" onclick="toggleSettingsDialog()">
    <path d="M0 0h24v24H0z" fill="none"/>
    <path d="M16.24 11.59c.09-.23.16-.47.16-.59s-.07-.36-.16-.59l1.56-1.22c.12-.09.15-.26.08-.39l-1.51-2.61c-.08-.14-.27-.18-.41-.1l-1.88 1c-.34-.25-.69-.47-1.06-.67l-.36-2.02c-.03-.14-.15-.25-.3-.25h-3.56c-.15 0-.27.11-.3.25l-.36 2.02c-.37.2-.72.42-1.06.67l-1.88-1c-.14-.08-.33-.04-.41.1l-1.51 2.61c-.07.13-.04.3.08.39l1.56 1.22c-.09.23-.16.47-.16.59s.07.36.16.59l-1.56 1.22c-.12.09-.15.26-.08.39l1.51 2.61c.08.14.27.18.41.1l1.88-1c.34.25.69.47 1.06.67l.36 2.02c.03.14.15.25.3.25h3.56c.15 0 .27-.11.3-.25l.36-2.02c.37-.2.72-.42 1.06-.67l1.88 1c.14.08.33.04.41-.1l1.51-2.61c.07-.13.04-.3-.08-.39zm-4.24 3.41c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2z"/>
</svg>

<div class="settings-dialog" id="settingsDialog">
    <h3>Settings</h3>
    <label for="fontSize">Font Size:</label>
    <select id="fontSize" onchange="changeFontSize()">
        <option value="14px">Small</option>
        <option value="16px" selected>Medium</option>
        <option value="18px">Large</option>
        <option value="20px">Extra Large</option>
    </select>

    <label for="fontFamily">Font Family:</label>
    <select id="fontFamily" onchange="changeFontFamily()">
        <option value="Arial, sans-serif" selected>Arial</option>
        <option value="Times New Roman, serif">Times New Roman</option>
        <option value="Verdana, sans-serif">Verdana</option>
    </select>

    <label for="bgColor">Background Color:</label>
    <select id="bgColor" onchange="changeBackgroundColor()">
        <option value="#fff" selected>White</option>
        <option value="#f0f0f0">Light Gray</option>
        <option value="#333">Dark Gray</option>
    </select>

    <button onclick="closeSettingsDialog()">Apply</button>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var fontSize = localStorage.getItem('fontSize');
        var fontFamily = localStorage.getItem('fontFamily');
        var bgColor = localStorage.getItem('bgColor');

        if (fontSize) {
            document.getElementById('fontSize').value = fontSize;
            document.getElementById('chapterContent').style.fontSize = fontSize;
        }
        if (fontFamily) {
            document.getElementById('fontFamily').value = fontFamily;
            document.getElementById('chapterContent').style.fontFamily = fontFamily;
        }
        if (bgColor) {
            document.getElementById('bgColor').value = bgColor;
            document.body.style.backgroundColor = bgColor;
        }
    });

    function toggleSettingsDialog() {
        var dialog = document.getElementById('settingsDialog');
        dialog.style.display = (dialog.style.display === 'block') ? 'none' : 'block';
    }

    function changeFontSize() {
        var fontSize = document.getElementById('fontSize').value;
        document.getElementById('chapterContent').style.fontSize = fontSize;
        localStorage.setItem('fontSize', fontSize);
    }

    function changeFontFamily() {
        var fontFamily = document.getElementById('fontFamily').value;
        document.getElementById('chapterContent').style.fontFamily = fontFamily;
        localStorage.setItem('fontFamily', fontFamily);
    }

    function changeBackgroundColor() {
        var bgColor = document.getElementById('bgColor').value;
        document.body.style.backgroundColor = bgColor;
        localStorage.setItem('bgColor', bgColor);
    }

    function closeSettingsDialog() {
        document.getElementById("settingsDialog").style.display = "none";
    }

</script>

</body>
</html>

<%
    } else {
        response.sendRedirect("error.jsp");
    }

    webnovelService.close();
%>