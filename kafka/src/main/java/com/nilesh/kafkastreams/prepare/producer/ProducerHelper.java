package com.nilesh.kafkastreams.prepare.producer;

import java.util.Map;
import java.util.Properties;

public class ProducerHelper{
    public Properties buildProperties(Map<String, String> producerConfigs, String bootStrapServers)
    {
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", bootStrapServers);
        for(String key : producerConfigs.keySet()) {
            String value = producerConfigs.get(key);
            if(key.equalsIgnoreCase("linger.ms"))
                producerProps.put(key, Integer.parseInt(value));
            else
                producerProps.put(key, value);
        }
        return producerProps;
    }
}
