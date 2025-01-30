package com.nilesh.kafkastreams.config;

import java.util.List;

public class ConsumerConfig {

    List<Consumer> consumers;

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
    }
}
