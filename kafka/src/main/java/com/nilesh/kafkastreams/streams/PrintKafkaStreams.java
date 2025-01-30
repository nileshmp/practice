package com.nilesh.kafkastreams.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Printed;
import java.util.Collection;
import java.util.Properties;

public class PrintKafkaStreams {

    private static final String APPLICATION_ID = "application-reset-demo";
    private static final String CLIENT_ID = "application-reset-demo-client";


    public static void main(String[] args) {

    }

    public void streamsConsumer(String bootStrapServers, Collection<String> topics) {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "print_stream_id-3");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final StreamsBuilder builder = new StreamsBuilder();
        // print or for-each caused the stream to terminate
        // whereas peek does not alter the state, mainly used for tracing, logging...etc
        builder.stream(topics)
                .peek((key, value) -> System.out.printf("Value: %s, Key:%s \n", value, key))
                .print(Printed.toSysOut());
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);
        kafkaStreams.start();
    }
}