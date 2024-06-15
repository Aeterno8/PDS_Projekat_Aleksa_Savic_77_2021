package com.example.webnovelapp.servlet;

import com.example.webnovelapp.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SessionValidationServlet", value = "/validate-session")
public class SessionValidationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SessionValidationServlet.class.getName());
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getParameter("sessionId");

        if (sessionId != null && userService.isSessionValid(sessionId)) {
            response.getWriter().write("Session is valid");
        } else {
            response.getWriter().write("Invalid session");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}