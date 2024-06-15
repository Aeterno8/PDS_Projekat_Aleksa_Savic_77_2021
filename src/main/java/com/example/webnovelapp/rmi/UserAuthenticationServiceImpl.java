package com.example.webnovelapp.rmi;

import com.example.webnovelapp.model.User;
import com.example.webnovelapp.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class UserAuthenticationServiceImpl extends UnicastRemoteObject implements UserAuthenticationService {

    private final UserService userService;

    public UserAuthenticationServiceImpl() throws RemoteException {
        super();
        this.userService = new UserService();
    }

    @Override
    public User getUserBySessionId(String sessionId) throws RemoteException {
        Optional<User> userOptional = userService.getUserBySessionId_NO_BEAN(sessionId);
        return userOptional.orElse(null);
    }
}