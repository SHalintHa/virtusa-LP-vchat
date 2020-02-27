package com.shalintha;

import com.sun.net.ssl.internal.ssl.Provider;

import java.io.IOException;
import java.security.Security;

public class Main {

    public static void main(String[] args) {
    // write your code here
    System.out.println("welcome");
        try {

            Client.connectToServer();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
