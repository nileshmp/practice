package com.nilesh.kafka.docker.controllers;

import com.nilesh.kafka.docker.CustomKafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final CustomKafkaTemplate<String, String> customKafkaTemplate;

    public HelloController(KafkaTemplate<String, String> kafkaTemplate) {
        this.customKafkaTemplate = new CustomKafkaTemplate<>(kafkaTemplate);
    }
    @GetMapping("/hello")
    public ResponseEntity<String> hello() throws Exception {
        logger.debug("Entered hello method");
        SendResult<String, String> send = customKafkaTemplate.send("test", "one", "one");
        return ResponseEntity.ok(send.toString());
    }
}
