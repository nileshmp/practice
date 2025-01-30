package com.nilesh.kafkastreams.config;

import java.util.List;
import java.util.Map;

public class ProducerConfig {

    List<Map<String, String>> producers;

    public List<Map<String, String>> getProducers() {
        return producers;
    }

    public void setProducers(List<Map<String, String>> producers) {
        this.producers = producers;
    }
}
