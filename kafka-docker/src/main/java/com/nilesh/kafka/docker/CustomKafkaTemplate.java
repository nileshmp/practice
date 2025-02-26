package com.nilesh.kafka.docker;

import com.nilesh.kafka.docker.controllers.HelloController;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

public class CustomKafkaTemplate<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CustomKafkaTemplate.class);

    @Autowired
    public CustomKafkaTemplate(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public SendResult<K, V> send(String topic, V value)
        throws ExecutionException, InterruptedException {
        final SendResult<K, V> result = kafkaTemplate.send(topic, value).get();
        return result;
    }

    public SendResult<K, V> send(String topic, K key, V value)
        throws ExecutionException, InterruptedException {
        logger.debug("Called SendResults with Key:{} and value:{}.", key, value);
        final SendResult<K, V> result = kafkaTemplate.send(topic, key, value).get();
        return result;
    }

    public SendResult<K, V> send(String topic, K key, int partition, V value)
        throws ExecutionException, InterruptedException {
        final SendResult<K, V> result = kafkaTemplate.send(topic, partition, key, value).get();
        return result;
    }
}
