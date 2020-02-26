package com.shalintha;

import java.util.ArrayList;
import java.util.List;

public class ChatMessages {

    ArrayList<Message> messages = new ArrayList<>();

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessages(String message) {
        messages.add(new Message(message, false));
    }
}


