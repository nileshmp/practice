package com.nilesh.kafkastreams.streams;

import com.nilesh.kafkastreams.streams.extractor.MyEventTimeExtractor;
import java.util.Collection;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Printed;

public class FlowControlStreams {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

    public void streamsConsumer(String bootStrapServers, Collection<String> topics) {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "print_stream_id-3");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, MyEventTimeExtractor.class);
        streamsConfiguration.put(StreamsConfig.BUFFERED_RECORDS_PER_PARTITION_CONFIG, "100");

        final StreamsBuilder builder = new StreamsBuilder();
        // print or for-each caused the stream to terminate
        // whereas peek does not alter the state, mainly used for tracing, logging...etc
        builder.stream(topics)
                .peek((key, value) -> System.out.printf("Value: %s, Key:%s \n", value, key))
                .print(Printed.toSysOut());
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);
        kafkaStreams.start();
    }

    public void streamsOffsetManagement(String bootStrapServers, Collection<String> topics)
    {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "print_stream_id-3");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, MyEventTimeExtractor.class);
        streamsConfiguration.put(StreamsConfig.BUFFERED_RECORDS_PER_PARTITION_CONFIG, "100");

        final StreamsBuilder builder = new StreamsBuilder();
        // print or for-each caused the stream to terminate
        // whereas peek does not alter the state, mainly used for tracing, logging...etc
        builder.stream(topics)
            .peek((key, value) -> System.out.printf("Value: %s, Key:%s \n", value, key))
            .print(Printed.toSysOut());
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);
        kafkaStreams.start();
        // kafkaStreams.setConsumer(new Consumer<>() {
        //     @Override
        //     public void seek(TopicPartition partition, long offset) {
        //         // Seek to a specific offset
        //         consumer.seek(partition, offset);
        //     }
        // });
    }
}

