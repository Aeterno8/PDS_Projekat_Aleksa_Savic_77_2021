package com.example.webnovelapp.servlet;

import com.example.webnovelapp.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String sessionId = (String) session.getAttribute("sessionId");
            session.invalidate();

            userService.getUserBySessionId(sessionId).ifPresent(userBean -> userService.logoutUser(userBean.getId()));
            response.getWriter().write("Logout successful");
        } else {
            response.getWriter().write("Logout failed: No active session");
        }
    }
}