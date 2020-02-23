package com.shalintha;

import com.sun.net.ssl.internal.ssl.Provider;

import java.security.Security;

public class Main {

    public static void main(String[] args) {
    // write your code here
    System.out.println("Hello");

        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.trustStore", "ChatKeyStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        Client client = new Client();
        client.connectToServer();

    }
}
