package com.nilesh.kafkastreams.prepare.container;

import com.nilesh.kafkastreams.config.KafkaConfig;
import com.nilesh.kafkastreams.config.KafkaYamlConfiguration;
import com.nilesh.kafkastreams.parsers.YAMLParser;
import org.testcontainers.kafka.KafkaContainer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;


public class KafkaServer {

    private KafkaContainer kafkaContainer;
    private Semaphore semaphore;

    public void setup(KafkaConfig kafkaConfig) {
        semaphore = new Semaphore(0);
        CountDownLatch latch = new CountDownLatch(1);
        Thread kafkaBrokerThread = new Thread(() -> {
            try {
                kafkaContainer = new KafkaContainer(kafkaConfig.getImage());
                kafkaContainer.start();
                kafkaConfig.setBootStrapServers(kafkaContainer.getBootstrapServers());
                System.out.println("Kafka broker started");
                latch.countDown();
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException("InterruptedException caught in lambda", e);
            }
        });
        kafkaBrokerThread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Starts the kafka docker container
        // kafkaContainer = new KafkaContainer(kafkaConfig.getImage());
        // kafkaContainer.start();
        // kafkaConfig.setBootStrapServers(kafkaContainer.getBootstrapServers());
        // KafkaAdmin kafkaAdmin = new KafkaAdmin(bootstrapServers);
        // for (Topic topic : kafkaConfig.getTopics())
        //     kafkaAdmin.createTopic(topic.getName(), topic.getPartitions(), topic.getReplication());
    }

    // public String bootStrapServers() {
    //     return kafkaContainer.getBootstrapServers();
    // }

    public void tearDown() {
        kafkaContainer.close();
        semaphore.release();
    }

    public static void main(String[] args) {
        KafkaYamlConfiguration kafkaYamlConfiguration = new YAMLParser<KafkaYamlConfiguration>().load(KafkaYamlConfiguration.class);
        KafkaConfig kafkaConfig = kafkaYamlConfiguration.getKafkaConfigs().stream().filter(kb -> kb.getType().equalsIgnoreCase("default")).findFirst().get();
        KafkaServer kafkaServer = new KafkaServer();
        kafkaServer.setup(kafkaConfig);
        System.out.println(kafkaConfig.getBootStrapServers());
    }
}