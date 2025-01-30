package com.nilesh.kafkastreams.helper;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.nilesh.kafkastreams.streams.Constants.BOOT_STRAP_SERVERS;
import static com.nilesh.kafkastreams.streams.Constants.inputTopic;

public class KafkaAdmin {

    private final Properties properties;

    public KafkaAdmin(String bootStrapServers) {
        properties = new Properties();
        properties.put(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        KafkaAdmin kafkaAdmin = new KafkaAdmin(BOOT_STRAP_SERVERS);
        // kafkaAdmin.deleteTopics();
        kafkaAdmin.createTopic(inputTopic, 1, 1);
        // kafkaAdmin.createTopic("output-topic-1");
        // kafkaAdmin.createTopic(TOPIC_DEMO_TEXAS_TOYOTA_SALES);
        // kafkaAdmin.listTopics();
        // kafkaAdmin.consumerDetails();
        kafkaAdmin.consume(inputTopic);
    }

    public void createTopic(String topicName,int partition, int replication) {
        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(24 * 60 * 60 * 1000)); // 24 hours retention
        NewTopic newTopic = new NewTopic(topicName, partition, (short) replication).configs(topicConfig);
        try (AdminClient adminClient = AdminClient.create(properties)) {
            // Blocking call to make sure topic is created
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void listTopics() throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListTopicsResult topics = adminClient.listTopics();
            Set<String> topicNames = topics.names().get();

            System.out.println("Topics in the Kafka cluster:");
            topicNames.forEach(System.out::println);
        }
    }

    public void deleteTopics() throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListTopicsResult topics = adminClient.listTopics();
            Set<String> topicNames = topics.names().get();
            topicNames.forEach(System.out::println);
            adminClient.deleteTopics(topicNames);
        }
    }

    public void consumerDetails() throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListConsumerGroupsResult listConsumerGroupsResult = adminClient.listConsumerGroups();
            KafkaFuture<Collection<ConsumerGroupListing>> consumerGroups = listConsumerGroupsResult.all();
            Collection<ConsumerGroupListing> consumerGroupListings = consumerGroups.get();
            for (ConsumerGroupListing consumerGroupListing : consumerGroupListings) {
                String groupId = consumerGroupListing.groupId();
                printOffsets(adminClient, groupId);
            }
        }
    }

    public void printOffsets(AdminClient adminClient, String groupId) {
        try {
            // Get offsets committed by the group
            Map<TopicPartition, OffsetAndMetadata> offsets = adminClient.listConsumerGroupOffsets(groupId).partitionsToOffsetAndMetadata().get();

            Map<TopicPartition, OffsetSpec> requestLatestOffsets = new HashMap<>();
            Map<TopicPartition, OffsetSpec> requestEarliestOffsets = new HashMap<>();

            // For all topics and partitions that have offsets committed by the group, get their latest offsets, earliest offsets
            for (TopicPartition tp : offsets.keySet()) {
                requestLatestOffsets.put(tp, OffsetSpec.latest());
                requestEarliestOffsets.put(tp, OffsetSpec.earliest());
            }

            Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets = adminClient.listOffsets(requestLatestOffsets).all().get();

            System.out.println("Consumer group ID : " + groupId);
            for (Map.Entry<TopicPartition, OffsetAndMetadata> e : offsets.entrySet()) {
                String topic = e.getKey().topic();
                int partition = e.getKey().partition();
                long committedOffset = e.getValue().offset();
                long latestOffset = latestOffsets.get(e.getKey()).offset();
                System.out.println("Topic: " + topic + " Partition: " + partition + " consumed: " + committedOffset + " produced: " + latestOffset + " Lag: " + (latestOffset - committedOffset));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consume(String topic) {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVERS);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + record.value());
            }
        }
    }
}