package com.nilesh.kafkastreams.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.*;

/**
 * Can produce 0, 1 or more values
 */
public class FlatMapStreams {
    public void streamsConsumer(String bootStrapServers, Collection<String> topics) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<Integer, String> stream = streamsBuilder.stream(topics, Consumed.with(Serdes.Integer(), Serdes.String()));
        stream.flatMap((key, value) ->
                {
                    List<KeyValue<Integer, String>> result = new LinkedList<>();
                    result.add(KeyValue.pair((key + 1000), value));
                    result.add(KeyValue.pair((key + 2000), value));
                    return result;
                })
                .foreach((key, value) -> System.out.printf("FlatMap, Key:%d, Value:%s \n", key, value));
        stream.flatMapValues(value -> Arrays.asList(value.split("\\s+")))
                .foreach((key,value) -> System.out.printf("FlatMapValues, Key:%d, Value:%s \n", key, value));
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
