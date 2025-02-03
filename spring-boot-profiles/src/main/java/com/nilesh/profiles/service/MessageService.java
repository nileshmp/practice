package com.nilesh.profiles.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    // Injecting the data source URL from the active profile
    @Value("${message}")
    private String envMessage;

    public String getEnvMessage() {
        return envMessage;
    }

    public void setEnvMessage(String envMessage) {
        this.envMessage = envMessage;
    }
}
