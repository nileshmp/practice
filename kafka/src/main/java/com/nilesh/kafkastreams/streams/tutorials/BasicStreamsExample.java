package com.nilesh.kafkastreams.streams.tutorials;

import com.nilesh.kafkastreams.streams.Constants;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;

import java.io.IOException;

public class BasicStreamsExample {
    public static void main(String[] args) throws IOException {
        Init.create_and_populate_topics();
        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        streamsBuilder.stream(Constants.inputTopic, Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(value -> Integer.parseInt(value) + 111)
                .peek((key, value) -> System.out.println(">>>>>>>>>>>>>>>>>>  Key: " + key + ", value: " + value));
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), Configs.buildStreamProperties("basic-streams-example"));
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
    }
}
