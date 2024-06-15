package com.example.webnovelapp.servlet;

import com.example.webnovelapp.service.UserService;
import com.example.webnovelapp.beans.UserBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RegistrationServlet", value = "/register")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private static final Logger logger = Logger.getLogger(RegistrationServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");

            logger.log(Level.INFO, "Received registration request with username: {0}, email: {1}, name: {2}, surname: {3}",
                    new Object[]{username, email, name, surname});

            String message = validateInput(username, password, email, name, surname);
            if (!message.isEmpty()) {
                logger.log(Level.WARNING, "Validation failed: {0}", message);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            Optional<UserBean> userBeanOptional = userService.registerUser(username, password, email, name, surname);

            if (userBeanOptional.isPresent()) {
                logger.log(Level.INFO, "Registration successful for username: {0}", username);
                response.sendRedirect("login.jsp");
            } else {
                message = "Registration failed: Username or Email already exists";
                logger.log(Level.WARNING, message);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in doPost method of RegistrationServlet", e);
            throw new ServletException("Error in doPost method of RegistrationServlet.", e);
        }
    }

    private String validateInput(String username, String password, String email, String name, String surname) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password is required.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email is required.";
        }
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }
        if (surname == null || surname.trim().isEmpty()) {
            return "Surname is required.";
        }
        return "";
    }
}