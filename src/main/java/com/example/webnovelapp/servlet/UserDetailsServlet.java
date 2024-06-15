package com.example.webnovelapp.servlet;

import com.example.webnovelapp.beans.UserBean;
import com.example.webnovelapp.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserDetailsServlet", value = "/user-details")
public class UserDetailsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UserDetailsServlet.class.getName());
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getParameter("sessionId");

        if (sessionId == null || sessionId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "SessionId parameter is required.");
            return;
        }

        try {
            Optional<UserBean> userBeanOptional = userService.getUserBySessionId(sessionId);

            if (userBeanOptional.isPresent()) {
                UserBean userBean = userBeanOptional.get();
                request.setAttribute("userBean", userBean);
                request.getRequestDispatcher("/user-details.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found for session id: " + sessionId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving user details", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving user details");
        }
    }

    @Override
    public void destroy() {
        userService.close();
        super.destroy();
    }
}