package com.shalintha;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private String userName;
    public static SSLSocket sslSocket;
    public static String serverIP;
    public static String serverPort;
    PrintWriter printWriter;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Client() {
    }

    public Client(String serverIP, String serverPort) {
        this.serverIP=serverIP;
        this.serverPort=serverPort;
    }

    public void connectToServer(){

        try {
            //vchat connect -h localhost -n shalintha
            System.out.println("Enter ip: ");
            Scanner input = new Scanner(System.in);
            String in = input.nextLine();
            sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(in, Integer.parseInt(serverPort));

            printWriter = new PrintWriter(sslSocket.getOutputStream(), true);
            printWriter.println(in);
            printWriter.flush();

            new WriteThread(sslSocket, this).start();
            new ReadThread(sslSocket, this).start();

//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (())
//                }
//            })


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
