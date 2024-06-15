package com.example.webnovelapp.websocket;

import com.example.webnovelapp.model.User;
import com.example.webnovelapp.service.UserService;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.Map;

public class ChatServerConfigurator extends ServerEndpointConfig.Configurator {

    private final UserService userService = new UserService();

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, Object> userProperties = sec.getUserProperties();
        String sessionId = request.getParameterMap().get("sessionId").get(0);
        User user = userService.getUserBySessionId_NO_BEAN(sessionId).orElse(null);

        if (user != null) {
            userProperties.put("user", user);
        } else {
            throw new IllegalStateException("Invalid session ID");
        }
    }
}