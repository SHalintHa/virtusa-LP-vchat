package com.shalintha;

import java.util.ArrayList;

public class Message {

    private String message;
    private boolean isSend;

    public Message(){   }

    public Message(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    ArrayList<Message> messages = new ArrayList<>();

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessages(String message) {
        messages.add(new Message(message, false));
    }
}
