package com.shalintha;


import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private int port;
//    private Map<String, UserThreads> users = new HashMap<>();
    private Set<String> users = new HashSet<>();
    private Set<UserThreads> userThreads = new HashSet<>();
    private PrintWriter printWriter;
    String serverIP;

    public Server(int port) {
        this.port = port;
    }

    public void startServer(){
        try {

            ServerSocket serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(port);
            serverIP = (InetAddress.getLocalHost().getHostAddress()).trim();
//            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started "+ serverIP + " on port " + port);
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("New User!");
                UserThreads newUser = new UserThreads(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String name){
        users.add(name);
    }

    public Set<String> getUsers() {
        return users;
    }

    public boolean getAUser(String name){
        if(users.contains(name)){
            return true;
        }
        else{
            return false;
        }
    }

    public void removeUser(String name, UserThreads userThread){
        users.remove(name);
        userThreads.remove(userThread);
        System.out.println(name + " offline!");
    }

    public boolean hasUsers(){
        return !this.users.isEmpty();
    }

    public void setUserThreads(UserThreads userThreads) {
        this.userThreads.add(userThreads);
    }
}
