package com.nilesh.kafkastreams.config;

import java.util.List;

public class KafkaYamlConfiguration {

    private List<KafkaConfig> kafkaConfigs;

    public List<KafkaConfig> getKafkaConfigs() {
        return kafkaConfigs;
    }
    public void setKafkaConfigs(List<KafkaConfig> kafkaConfigs) {
        this.kafkaConfigs = kafkaConfigs;
    }
}

