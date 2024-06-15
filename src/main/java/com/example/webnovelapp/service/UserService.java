package com.example.webnovelapp.service;

import com.example.webnovelapp.beans.UserBean;
import com.example.webnovelapp.model.User;
import com.example.webnovelapp.repository.UserRepository;
import com.example.webnovelapp.util.PasswordUtil;

import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public Optional<UserBean> registerUser(String username, String password, String email, String name, String surname) {
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return Optional.empty();
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        User newUser = new User(username, hashedPassword, email, name, surname, true);
        userRepository.save(newUser);
        return Optional.of(convertToBean(newUser));
    }

    public Optional<UserBean> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (PasswordUtil.verifyPassword(password, user.getPassword())) {
                String sessionId = generateSessionId();
                user.setSessionId(sessionId);
                userRepository.save(user);
                return Optional.of(convertToBean(user));
            }
        }
        return Optional.empty();
    }

    public Optional<UserBean> getUserBySessionId(String sessionId) {
        Optional<User> userOptional = userRepository.findBySessionId(sessionId);
        return userOptional.map(this::convertToBean);
    }

    public Optional<User> getUserBySessionId_NO_BEAN(String sessionId) {
        return userRepository.findBySessionId(sessionId);
    }

    public boolean logoutUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSessionId(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean changePassword(Long userId, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private UserBean convertToBean(User user) {
        return new UserBean(user.getId(), user.getUsername(), null, user.getEmail(), user.getName(), user.getSurname(), user.isEnabled(), user.getSessionId());
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public boolean isSessionValid(String sessionId) {
        return userRepository.findBySessionId(sessionId).isPresent();
    }

    public void close() {
        userRepository.close();
    }
}