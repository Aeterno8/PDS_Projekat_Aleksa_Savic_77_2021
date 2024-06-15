package com.example.webnovelapp.beans;

import java.time.LocalDateTime;

public class ChatMessageBean {

    private Long id;
    private UserBean user;
    private String content;
    private LocalDateTime timestamp;
    private ChatRoomBean chatRoom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ChatRoomBean getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoomBean chatRoom) {
        this.chatRoom = chatRoom;
    }
}