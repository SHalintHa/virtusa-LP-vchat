package com.shalintha;

import java.io.*;
import java.net.Socket;

public class UserThreads extends Thread  {
    private Socket socket;
    private Server server;
    private PrintWriter printWriter;
    private String serverMessage;

    public UserThreads(Socket socket, Server server){
        this.socket=socket;
        this.server=server;
    }


    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);

            String userName = bufferedReader.readLine();
            server.addUser(userName);
            System.out.println("New user connected : " + userName);

            String message="Hello World!";

            while (true){
                if(message.equals("exit") || message.equals("Exit"))
                    break;
                message = bufferedReader.readLine();
                serverMessage = userName  + ": " + message;

            }
            server.removeUser(userName, this);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getAllUsers(){
        if(server.hasUsers()){
            printWriter.println(server.getUsers());
        }
        else{
            printWriter.println("No users");
        }
    }
}
