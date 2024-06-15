package com.example.webnovelapp.websocket;

import com.example.webnovelapp.model.ChatMessage;
import com.example.webnovelapp.model.User;
import com.example.webnovelapp.service.ChatService;
import com.example.webnovelapp.service.UserService;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat", configurator = ChatServerConfigurator.class)
public class ChatServer {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private final ChatService chatService;
    private final UserService userService;

    public ChatServer() {
        chatService = new ChatService();
        userService = new UserService();
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Session opened: " + session.getId());

        List<ChatMessage> chatHistory = chatService.getMessagesFromGlobalChatRoom();
        for (ChatMessage message : chatHistory) {
            sendToSession(session, formatMessage(message));
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        User user = (User) session.getUserProperties().get("user");
        if (user != null) {
            ChatMessage chatMessage = new ChatMessage(user, message, chatService.getGlobalChatRoom());
            chatService.addMessageToGlobalChatRoom(user, message);
            broadcastMessage(chatMessage);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }

    private void broadcastMessage(ChatMessage message) {
        String formattedMessage = formatMessage(message);
        for (Session session : sessions) {
            sendToSession(session, formattedMessage);
        }
    }

    private void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatMessage(ChatMessage message) {
        return "{\"timestamp\":\"" + message.getTimestamp() + "\",\"user\":{\"username\":\"" + message.getUser().getUsername() + "\"},\"content\":\"" + message.getContent() + "\"}";
    }
}