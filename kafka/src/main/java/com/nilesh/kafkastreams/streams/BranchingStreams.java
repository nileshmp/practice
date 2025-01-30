package com.nilesh.kafkastreams.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Collection;
import java.util.Properties;

public class BranchingStreams {
    public void streamsConsumer(String bootStrapServers, Collection<String> topics) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<Integer, String> stream = streamsBuilder.stream(topics, Consumed.with(Serdes.Integer(), Serdes.String()));
        KStream<Integer, String> evenStream = stream.filter((key, value) -> key % 2 == 0);
        evenStream
                .foreach((key, value) -> System.out.printf("Even value stream Key:%d, Value:%s \n", key, value));
        stream.filterNot((key, value) -> key % 2 == 0)
                .foreach((key, value) -> System.out.printf("Odd value stream Key:%d, Value:%s \n", key, value));
        streamsBuilder.build();
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), buildProperties(bootStrapServers));
        kafkaStreams.start();
    }

    private Properties buildProperties(String bootStrapServers) {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-id-1");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        return streamsConfiguration;
    }
}
