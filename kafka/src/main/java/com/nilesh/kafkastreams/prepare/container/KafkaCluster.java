package com.nilesh.kafkastreams.prepare.container;

import com.nilesh.kafkastreams.config.KafkaConfig;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.kafka.KafkaContainer;

public class KafkaCluster {

    private Collection<KafkaContainer> brokers;
    private Network network;
    private Semaphore semaphore;

    public void setup(KafkaConfig kafkaConfig) {
        System.out.println("KafkaCluster setup called");
        semaphore = new Semaphore(0);
        CountDownLatch latch = new CountDownLatch(1);
        Thread kafkaBrokerThread = new Thread(() -> {
            try {
                this.network = Network.newNetwork();
                List<Map<String, String>> brokerConfigs =
                    kafkaConfig.getBrokerConfigs().getBrokers();
                this.brokers = IntStream
                    .range(0, brokerConfigs.size())
                    .mapToObj(brokerNum -> new KafkaContainer(kafkaConfig.getImage())
                        .withNetwork(this.network)
                        .withNetworkAliases("broker-" + brokerNum)
                        // .dependsOn(this.zookeeper)
                        // .withExternalZookeeper("zookeeper:" + KafkaContainer.ZOOKEEPER_PORT)
                        // .withEnv("KAFKA_BROKER_ID", brokerNum + "")
                        // .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", kafkaConfig.getReplication() + "")
                        // .withEnv("KAFKA_OFFSETS_TOPIC_NUM_PARTITIONS", kafkaConfig.getPartition() + "")
                        // .withEnv("KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR", kafkaConfig.getReplication() + "")
                        // .withEnv("KAFKA_TRANSACTION_STATE_LOG_MIN_ISR", kafkaConfig.getISR() + "")
                        .withStartupTimeout(Duration.ofMinutes(kafkaConfig.getStartupTimeout())))
                    .collect(Collectors.toList());
                brokers.forEach(KafkaContainer::start);
                // System.out.println(brokers);
                kafkaConfig.setBootStrapServers(
                    brokers.stream().map(KafkaContainer::getBootstrapServers)
                        .collect(Collectors.joining(",")));
                System.out.println("------ Kafka Cluster started");
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
    }

    private Stream<KafkaContainer> allContainers() {
        return this.brokers.stream();
    }

    public String bootStrapServers() {
        return brokers.stream().map(KafkaContainer::getBootstrapServers)
            .collect(Collectors.joining(","));
    }

    public void tearDown() {
        allContainers().parallel().forEach(GenericContainer::stop);
        semaphore.release();
    }
}
