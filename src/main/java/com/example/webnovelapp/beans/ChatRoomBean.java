package com.example.webnovelapp.beans;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomBean {

    private Long id;
    private String name;
    private List<ChatMessageBean> messages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChatMessageBean> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessageBean> messages) {
        this.messages = messages;
    }
}