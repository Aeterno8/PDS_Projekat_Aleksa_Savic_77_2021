package com.example.webnovelapp.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static final int PORT = 12097;

    public static void main(String[] args) {
        try {
            UserAuthenticationService userService = new UserAuthenticationServiceImpl();
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind("UserAuthenticationService", userService);
            System.out.println("RMI Server started on port " + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}