package com.shalintha;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private static String POST_URL = "";
    private static String POST_PARAMS = "";
    private static String text, name;
    private static Pattern pattern;
    private static Matcher matcher;

    public static void connectToServer() throws IOException {

        boolean isConnected = false;

        Scanner scanner = new Scanner(System.in);

        while (!isConnected) {
            text = scanner.nextLine();
            pattern = Pattern.compile("(^connect)\\s(\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b):(\\b\\d{4}\\b)\\s(\\bas\\b)\\s(\\b[A-Za-z]{3,15})$");
            matcher = pattern.matcher(text);

            if (matcher.find()) {
                name = matcher.group(5);
                POST_PARAMS = "name=" + matcher.group(5);
                POST_URL = "http://" + matcher.group(2) + ":" + matcher.group(3);
                sendPOSTReqest(POST_PARAMS);
                isConnected = true;
            } else {
                System.out.println("Invalid Command\n");
            }

        }
}

    private static void sendPOSTReqest(String postParams) throws IOException {
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postParams.getBytes());
        os.close();

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (response.toString().length() > 0) {
                System.out.println(response.toString().replace(",", "\n"));
                if ("User not found".equals(response.toString())) {
                    connectToServer();
                }
            }
        } else {
            System.out.println("Invalid request");
        }
    }
}
