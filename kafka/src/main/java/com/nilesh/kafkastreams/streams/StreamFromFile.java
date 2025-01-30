package com.nilesh.kafkastreams.streams;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

public class StreamFromFile {
    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        System.out.println("#2###################################################################################################################################################################################");
        KStream<String, String> source = builder.stream("iot-temperature");
    }
}
