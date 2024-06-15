package com.example.webnovelapp.servlet;

import com.example.webnovelapp.service.UserService;
import com.example.webnovelapp.beans.UserBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String message = validateInput(username, password);
            if (!message.isEmpty()) {
                request.setAttribute("message", message);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            Optional<UserBean> userBeanOptional = userService.loginUser(username, password);

            if (userBeanOptional.isPresent()) {
                UserBean userBean = userBeanOptional.get();
                HttpSession session = request.getSession();
                session.setAttribute("sessionId", userBean.getSessionId());
                response.sendRedirect("index.jsp");
            } else {
                message = "Login failed: Invalid username or password";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error in doPost method of LoginServlet.", e);
        }
    }

    private String validateInput(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password is required.";
        }
        return "";
    }
}