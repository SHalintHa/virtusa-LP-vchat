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
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    private static int port = 8080;
    static Map<String, Message> clients = new HashMap<>();

    static InetAddress inetAddress;

    public static void start(){

        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", Server::handler);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            httpServer.setExecutor(threadPoolExecutor);
            httpServer.start();
            System.out.println("Server Started.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handler(HttpExchange httpExchange) throws IOException {
        String response = "", request = "";
        Matcher matcher;
        Pattern pattern = Pattern.compile("(^[a-zA-Z]*\\b)=(.*)");
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
            clients.put(stringMap.get("name"), new Message());
            System.out.println("New User " + stringMap.get("name"));
            response = "Connected to Server";
        }
        else{
            if ("list".equals(stringMap.get("console"))){
                for(Map.Entry<String, String> entry : stringMap.entrySet()){
                    if(entry.getKey().equals(stringMap.get("name"))){
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
                    Message chatMessages;
                    if((chatMessages = clients.get(stringMap.get("to")))!=null){
                        chatMessages.addMessages(stringMap.get("name") + " : " + stringMap.get("message") + "\n");
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
