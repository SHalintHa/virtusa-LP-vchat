package com.shalintha;


import com.sun.net.ssl.internal.ssl.Provider;

import java.security.Security;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore", "TheKeyStore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        Server chatServer = new Server(8191);
        chatServer.startServer();
    }
}
