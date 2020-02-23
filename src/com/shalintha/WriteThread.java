package com.shalintha;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private PrintWriter printWriter;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            Console console = System.console();
            String userName = console.readLine("Enter user name: ");
            client.setUserName(userName);
            String message = console.readLine();
            printWriter.println(message);

            while (true) {
                if (message.equals("exit") || message.equals("Exit"))
                    break;
                message = console.readLine();
                message = userName + ": " + message;

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
