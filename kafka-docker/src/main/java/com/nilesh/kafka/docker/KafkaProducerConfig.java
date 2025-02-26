package com.nilesh.kafka.docker;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

// @Configuration
public class KafkaProducerConfig {

    // @Bean
    // public KafkaTemplate<String, String> kafkaTemplate() {
    //     return new KafkaTemplate<>(producerFactory());
    // }
    //
    // @Bean
    // public ProducerFactory<String, String> producerFactory() {
    //     return new DefaultKafkaProducerFactory<>(producerConfigs());
    // }

    // @Bean
    // public Map<String, Object> producerConfigs() {
    //     Map<String, Object> configs = new HashMap<>();
    //     configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    //     configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     return configs;
    // }

    // @Bean
    // public NewTopic topicWithConfig() {
    //     Map<String, String> config = Map.of(
    //         "retention.ms", "3600000",  // Retention time in milliseconds (1 hour)
    //         "cleanup.policy", "compact" // Cleanup policy for compacting the topic
    //     );
    //     return new NewTopic("test", 1, (short) 1).configs(config);
    // }
}
