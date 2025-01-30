package com.nilesh.kafkastreams.config;

import java.util.List;
import java.util.Map;

public class BrokerConfig {

    private List<Map<String, String>> brokers;

    public List<Map<String, String>> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<Map<String, String>> brokers) {
        this.brokers = brokers;
    }
}
