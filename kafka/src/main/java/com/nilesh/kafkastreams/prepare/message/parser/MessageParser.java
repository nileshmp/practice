package com.nilesh.kafkastreams.prepare.message.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilesh.kafkastreams.prepare.message.beans.Messages;

import java.io.IOException;
import java.io.InputStream;

public class MessageParser {
    public Messages load(String filePath) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(filePath);
        ObjectMapper mapper = new ObjectMapper();
        Messages messages = mapper.readValue(inputStream, Messages.class);
        return messages;
    }

    public static void main(String[] args) throws IOException {
        MessageParser messageParser = new MessageParser();
        messageParser.load("/messages.json");
    }
}
