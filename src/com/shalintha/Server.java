package com.shalintha;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {


    private Scanner scanner;
    private int port = 8080;
    static Map<String, ChatMessages> clients = new HashMap<>();

    static String serverIP;
    static InetAddress inetAddress;

    public static String getServerIP(){
        return inetAddress.getHostAddress().trim();
    }

    private void start(){

        try {
            while (true){
                HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);

                httpServer.createContext("/test", Server::handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handler(HttpExchange httpExchange) throws IOException {
        String response = "", request = "";
        Matcher matcher;
        Pattern pattern = Pattern.compile("(^[a-zA-Z]*\\b)=(.*)");
        Matcher matcher1;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));

        String input = bufferedReader.readLine();
        while(!(input==null)){
            request = request + input;
        }
        bufferedReader.close();

        Map<String, String> stringMap = new HashMap<>();
        String split[] = request.split(",");

        for(String p : split){
            matcher = pattern.matcher(p);
            if(matcher.find()){
                stringMap.put(matcher.group(1), matcher.group(2));
            }
        }

        if(clients.get(stringMap.get("name"))==null){
            clients.put(stringMap.get("name"), new ChatMessages());
            System.out.println("New User " + stringMap.get("name"));
            response = "Connected to Server";
        }
        else{
            if ("list".equals(stringMap.get("console"))){
                for(Map.Entry<String, String> entry : stringMap.entrySet()){
                    if(entry.getKey().toLowerCase().equals(stringMap.get("name").toLowerCase())){
                        response += entry.getKey() + "[you]"+"\n";
                    }
                    else{
                        response += entry.getKey() + "\n";
                    }
                }
                System.out.println(response);
            }
            else if(stringMap.get("message")!=null && stringMap.get("to")!=null && stringMap.get("name")!=null){
                System.out.println(clients);
                System.out.println(stringMap.get("to"));
                if(clients.get(stringMap.get("to"))==null){
                    response="User not found";
                }
                else{
                    ChatMessages chatMessages;
                    if((chatMessages = clients.get(stringMap.get("to")))!=null){
                        chatMessages.addMessage(stringMap.get("name") + " : " + stringMap.get("message") + "\n");
                    }
                    else{
                        response = "User Not found ";
                    }
                }
            }
        }
        sendResponse(response, httpExchange);

    }

    private static void sendResponse(String response, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
