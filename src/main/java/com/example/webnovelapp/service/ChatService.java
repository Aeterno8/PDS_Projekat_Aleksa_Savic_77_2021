package com.example.webnovelapp.service;

import com.example.webnovelapp.model.ChatMessage;
import com.example.webnovelapp.model.ChatRoom;
import com.example.webnovelapp.model.User;
import com.example.webnovelapp.repository.ChatMessageRepository;
import com.example.webnovelapp.repository.ChatRoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ChatService {

    private static final String GLOBAL_CHAT_ROOM_NAME = "GlobalChatRoom";
    private static ChatRoom globalChatRoom;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatService() {
        chatRoomRepository = new ChatRoomRepository();
        chatMessageRepository = new ChatMessageRepository();
        initializeGlobalChatRoom();
    }

    private void initializeGlobalChatRoom() {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByName(GLOBAL_CHAT_ROOM_NAME);
        if (chatRoomOptional.isPresent()) {
            globalChatRoom = chatRoomOptional.get();
        } else {
            globalChatRoom = new ChatRoom();
            globalChatRoom.setName(GLOBAL_CHAT_ROOM_NAME);
            chatRoomRepository.save(globalChatRoom);
        }
    }

    public ChatRoom getGlobalChatRoom() {
        return globalChatRoom;
    }

    public void addMessageToGlobalChatRoom(User user, String content) {
        ChatMessage chatMessage = new ChatMessage(user, content, globalChatRoom);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesFromGlobalChatRoom() {
        return chatMessageRepository.findByChatRoomId(globalChatRoom.getId());
    }

    public void close() {
        chatRoomRepository.close();
        chatMessageRepository.close();
    }
}