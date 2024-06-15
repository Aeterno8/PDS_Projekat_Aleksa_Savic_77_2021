package com.example.webnovelapp.rmi;

import com.example.webnovelapp.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAuthenticationService extends Remote {
    User getUserBySessionId(String sessionId) throws RemoteException;
}