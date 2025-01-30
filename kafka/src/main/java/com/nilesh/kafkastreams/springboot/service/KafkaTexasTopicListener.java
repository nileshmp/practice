package com.nilesh.kafkastreams.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaTexasTopicListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTexasTopicListener.class);

    @KafkaListener(topics = "${spring.kafka.topic.texassales}")
    public void readRxClaimStream(@Payload String record) {
        logger.info(record);
        if(record!=null && !record.isEmpty()) {
            try {
                logger.info("TEXAS TOPIC => {}", record);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}