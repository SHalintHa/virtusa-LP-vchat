package com.shalintha;


public class Main {

    public static void main(String[] args) {
	// write your code here
        Server chatServer = new Server(8191);
        chatServer.startServer();
    }
}
