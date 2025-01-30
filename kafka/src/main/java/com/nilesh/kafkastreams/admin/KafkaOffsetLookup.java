package com.nilesh.kafkastreams.admin;

import com.nilesh.kafkastreams.config.KafkaConfig;
import com.nilesh.kafkastreams.config.KafkaYamlConfiguration;
import com.nilesh.kafkastreams.config.Topic;
import com.nilesh.kafkastreams.parsers.YAMLParser;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.StreamsConfig;

public class KafkaOffsetLookup {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // "23-11-2024 12:00"
        long timestamp = 1732548659921L;
        String bootStrapServers = "localhost:32775";
        Properties streamsConfig = new Properties();
        streamsConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-offset-lookup");
        streamsConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        KafkaYamlConfiguration kafkaYamlConfiguration =
            new YAMLParser<KafkaYamlConfiguration>().load(KafkaYamlConfiguration.class);
        KafkaConfig kafkaConfig = kafkaYamlConfiguration.getKafkaConfigs().stream()
            .filter(kb -> kb.getType().equalsIgnoreCase("default")).findFirst().get();
        Set<String> topics = kafkaConfig.getTopics().stream()
            .map(Topic::getName)
            .collect(Collectors.toSet());
        KafkaOffsetLookup kafkaOffsetLookup = new KafkaOffsetLookup();
        for (Topic topic : kafkaConfig.getTopics()) {
            kafkaOffsetLookup.lookupOffset(streamsConfig, kafkaOffsetLookup.convert2Datetime(timestamp), topic.getName(),
                topic.getPartitions());
        }
    }

    private String convert2Datetime(long epochLong) {
        Instant instant = Instant.ofEpochMilli(epochLong);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS z");
        return localDateTime.format(formatter);
    }

    /**
     *
     * @param streamsConfig
     * @param dateTime Example: "dd/MM/yyyy HH:mm:ss.SSS z"
     * @param topic
     * @param partitions
     */
    private void lookupOffset(Properties streamsConfig, String dateTime, String topic, int partitions) {
        long timestamp = convert2Epoch(dateTime);
        try (AdminClient adminClient = AdminClient.create(streamsConfig)) {
            List<TopicPartition> topicPartitions = new ArrayList<>();
            for (int paritionIndex = 0; paritionIndex < partitions; paritionIndex++) {
                topicPartitions.add(new TopicPartition(topic, paritionIndex));
            }
            Map<TopicPartition, OffsetSpec> request = new HashMap<>();
            for (TopicPartition tp : topicPartitions) {
                request.put(tp, OffsetSpec.forTimestamp(timestamp));
            }
            KafkaFuture<Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo>>
                mapKafkaFuture = adminClient.listOffsets(request).all();
            Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo>
                topicPartitionListOffsetsResultInfoMap = mapKafkaFuture.get();
            for (TopicPartition topicPartition : topicPartitionListOffsetsResultInfoMap.keySet()) {
                ListOffsetsResult.ListOffsetsResultInfo listOffsetsResultInfo =
                    topicPartitionListOffsetsResultInfoMap.get(topicPartition);
                System.out.println(listOffsetsResultInfo);
                if (listOffsetsResultInfo != null) {
                    System.out.println("Partition: " + topicPartition.partition() +
                        " Offset: " + listOffsetsResultInfo.offset() +
                        " Timestamp: " + listOffsetsResultInfo.timestamp());
                } else {
                    System.out.println("No offset found for timestamp " + timestamp +
                        " in partition " + topicPartition.partition());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example: "dd/MM/yyyy HH:mm:ss.SSS z"
    private long convert2Epoch(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS z");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, formatter);
        return zonedDateTime.toInstant().toEpochMilli();
    }
}
